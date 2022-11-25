package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.config.KafkaConfig
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.BlockPeriod
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpBlockAction
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpRecord
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.Action
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.IpActionMessage
import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.IpBlockActionRepository
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpBlockActionAlreadyExistException
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpBlockActionNotFoundException
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
                               private val kafkaTemplateMessage: KafkaTemplate<String, IpActionMessage>
)
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
                val message = IpActionMessage(record.id!!, record.ip, Action.ADD)
                val future = kafkaTemplateMessage.send(KafkaConfig.INSERT_IP_TOPIC, record.id!!.toString(), message)
                future.get()
                save(IpBlockAction(isActive = true, blockPeriod = blockPeriod,
                    user = userService.getCurrentUser(), record = record))
            }
            else -> {
                val action = repository.getOneByRecord_IdAndIsActiveIsTrue(record.id!!)
                if (action == null) {
                    save(IpBlockAction(isActive = true, blockPeriod = blockPeriod,
                        user = userService.getCurrentUser(), record = record))
                } else throw IpBlockActionAlreadyExistException(ip)
            }
        }
    }

    @Transactional
    override fun unblockIp(id: Long) {
        val action = repository.getOneById(id) ?: throw IpBlockActionNotFoundException()
        action.apply {
            endTime = LocalDateTime.now()
            isSynchronized = false
            userExclude = userService.getCurrentUser()
        }
        val message = IpActionMessage(action.record.id!!, action.record.ip, Action.REMOVE)
        kafkaTemplateMessage.send(KafkaConfig.DELETE_IP_TOPIC, action.record.id!!.toString(), message)
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