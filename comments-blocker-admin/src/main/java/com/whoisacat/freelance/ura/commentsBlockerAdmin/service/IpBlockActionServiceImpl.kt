package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.config.KafkaConfig
import com.whoisacat.freelance.ura.domain.BlockPeriod
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpBlockAction
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpRecord
import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.IpBlockActionRepository
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpBlockActionAlreadyExistRequestException
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpBlockActionNotFoundRequestException
import com.whoisacat.freelance.ura.kafka.dto.Action
import com.whoisacat.freelance.ura.kafka.dto.IpActionMessage
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class IpBlockActionServiceImpl(private val repository: IpBlockActionRepository,
                               @Lazy private val recordsService: IpRecordService,
                               val userService: UserService,
                               private val kafkaTemplateMessage: KafkaTemplate<String, IpActionMessage>? = null)
    : IpBlockActionService {


    @Scheduled(fixedRate = 1, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    fun checkIfEnd() {
        val list = mutableListOf<IpBlockAction>()
        list.apply {
            addAll(repository
                .findAllByIsActiveIsTrueAndBlockPeriodAndStartTimeIsBefore(BlockPeriod.ONE_HOUR,
                LocalDateTime.now().minusHours(1)))
            addAll(repository
                .findAllByIsActiveIsTrueAndBlockPeriodAndStartTimeIsBefore(BlockPeriod.FOUR_HOURS,
                    LocalDateTime.now().minusHours(4)))
            addAll(repository
                .findAllByIsActiveIsTrueAndBlockPeriodAndStartTimeIsBefore(BlockPeriod.ONE_DAY,
                    LocalDateTime.now().minusDays(1)))
            addAll(repository
                .findAllByIsActiveIsTrueAndBlockPeriodAndStartTimeIsBefore(BlockPeriod.ONE_WEEK,
                    LocalDateTime.now().minusDays(7)))
        }
        list.forEach{
            it::apply {
                isActive = false
                isSynchronized = false
                endTime = LocalDateTime.now()
                save(this)
                sendToKafkaIfKafkaTemplateExists(record.ip, record.id!!, Action.REMOVE,
                    KafkaConfig.DELETE_IP_TOPIC)
            }
        }
    }

    @Transactional(readOnly = true)
    override fun findPageByUser(pageRequest: PageRequest, text: String): Page<IpBlockAction> {
        return repository
            .findPageByUser_FirstNameContainsOrUser_LastNameContains(text, text, pageRequest)
    }

    @Transactional(readOnly = true)
    override fun findActivePageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findPageByIsActiveIsTrueAndRecord_IdIsIn(ids, pageRequest)
    }

    @Transactional(readOnly = true)
    override fun findPageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findPageByRecord_IdIsIn(ids, pageRequest)
    }

    @Transactional
    override fun save(action: IpBlockAction): IpBlockAction {
        return repository.save(action)
    }

    @Transactional
    override fun blockIp(ip: String, blockPeriod: BlockPeriod) {
        var record = recordsService.getOneByIp(ip) ?: IpRecord(ip = ip)
        when (record.id) {
            null -> {
                record = recordsService.save(record)
                sendToKafkaIfKafkaTemplateExists(record.ip, record.id!!, Action.ADD,
                    KafkaConfig.INSERT_IP_TOPIC)
                save(IpBlockAction(isActive = true, blockPeriod = blockPeriod,
                    user = userService.getCurrentUser(), record = record))
            }
            else -> {
                val action = repository.getOneByRecord_IdAndIsActiveIsTrue(record.id!!)
                if (action == null) {
                    sendToKafkaIfKafkaTemplateExists(record.ip, record.id!!, Action.ADD,
                        KafkaConfig.INSERT_IP_TOPIC)
                    save(IpBlockAction(isActive = true, blockPeriod = blockPeriod,
                        user = userService.getCurrentUser(), record = record))
                } else throw IpBlockActionAlreadyExistRequestException(ip)
            }
        }
    }

    private fun sendToKafkaIfKafkaTemplateExists(ip: String, id: Long, action: Action, topic: String) {
        if (kafkaTemplateMessage == null) return
        val message = IpActionMessage(ip, action)
        val future = kafkaTemplateMessage.send(topic, id.toString(), message)
        future.get()
    }

    @Transactional
    override fun unblockIp(id: Long) {
        val action = repository.getOneById(id) ?: throw IpBlockActionNotFoundRequestException()
        action.apply {
            endTime = LocalDateTime.now()
            isSynchronized = false
            userExclude = userService.getCurrentUser()
        }
        sendToKafkaIfKafkaTemplateExists(action.record.ip, action.record.id!!, Action.REMOVE,
            KafkaConfig.DELETE_IP_TOPIC)
        save (action)
    }

    @Transactional(readOnly = true)
    override fun getPage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findAllByIsActiveIsTrueOrderByStartTimeDesc(pageRequest)
    }

    @Transactional(readOnly = true)
    override fun getNotActivePage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.getAllByOrderByStartTimeDesc(pageRequest)
    }
}