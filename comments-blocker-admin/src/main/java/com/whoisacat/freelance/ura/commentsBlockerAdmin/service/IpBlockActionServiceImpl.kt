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
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class IpBlockActionServiceImpl(private val repository: IpBlockActionRepository,
    @Lazy private val recordsService: IpRecordService,
    val userService: UserService)
    : IpBlockActionService {

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
                save(IpBlockAction(
                    isActive = true,
                    blockPeriod = blockPeriod,
                    user = userService.getCurrentUser(),
                    record = record,
                ))
            }
            else -> {
                val action = repository.getOneByRecord_IdAndIsActiveIsTrue(record.id!!)
                if (action != null) throw IpBlockActionAlreadyExistException(ip)
                else {
                    save(IpBlockAction(
                        isActive = true,
                        blockPeriod = blockPeriod,
                        user = userService.getCurrentUser(),
                        record = record,
                    ))
                }
            }
        }
    }

    override fun unblockIp(id: Long) {
        val action = repository.getOneById(id) ?: throw IpBlockActionNotFoundException()
        action.endTime = LocalDateTime.now()
        action.userExclude = userService.getCurrentUser()
        save (action)
    }

    override fun getPage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findAllByIsActiveIsTrueOrderByStartTimeDesc(pageRequest)
    }

    override fun getNotActivePage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.getAllByOrderByStartTimeDesc(pageRequest)
    }
}