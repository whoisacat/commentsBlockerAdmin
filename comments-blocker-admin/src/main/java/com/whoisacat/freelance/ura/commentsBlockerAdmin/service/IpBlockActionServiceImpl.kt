package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.BlockPeriod
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpBlockAction
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpRecord
import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.IpBlockActionRepository
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpBlockActionAlreadyExistException
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpBlockActionNotFoundException
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class IpBlockActionServiceImpl(private val repository: IpBlockActionRepository,
    @Lazy private val recordsService: IpRecordService,
    val userService: UserService)
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

    override fun findPageByUser(pageRequest: PageRequest, text: String): Page<IpBlockAction> {
        return repository
            .findPageByUser_FirstNameContainsOrUser_LastNameContains(text, text, pageRequest)
    }

    override fun findActivePageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findPageByIsActiveIsTrueAndRecord_IdIsIn(ids, pageRequest)
    }

    override fun findPageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findPageByRecord_IdIsIn(ids, pageRequest)
    }

    override fun save(action: IpBlockAction): IpBlockAction {
        return repository.save(action)
    }

    override fun blockIp(ip: String, blockPeriod: BlockPeriod) {
        var record = recordsService.getOneByIp(ip) ?: IpRecord(ip = ip)
        when (record.id) {
            null -> {
                record = recordsService.save(record)
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

    override fun unblockIp(id: Long) {
        val action = repository.getOneById(id) ?: throw IpBlockActionNotFoundException()
        action.apply {
            endTime = LocalDateTime.now()
            isSynchronized = false
            userExclude = userService.getCurrentUser()
        }
        save (action)
    }

    override fun getPage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findAllByIsActiveIsTrueOrderByStartTimeDesc(pageRequest)
    }

    override fun getNotActivePage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.getAllByOrderByStartTimeDesc(pageRequest)
    }
}