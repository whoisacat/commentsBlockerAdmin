package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.domain.IpBlockAction
import com.whoisacat.freelance.ura.fileUpdater.repository.IpBlockActionRepository
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class IpBlockActionServiceImpl(private val repository: IpBlockActionRepository,
    @Lazy private val recordsService: IpRecordService)
    : IpBlockActionService {

    override fun findActivePageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findPageByIsActiveIsTrueAndRecord_IdIsIn(ids, pageRequest)
    }

    override fun findPageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findPageByRecord_IdIsIn(ids, pageRequest)
    }

    override fun save(action: IpBlockAction): IpBlockAction {
        return repository.save(action)
    }

    override fun getActivePage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findAllByIsActiveIsTrueOrderByStartTimeDesc(pageRequest)
    }

    override fun getNotActivePage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.getAllByOrderByStartTimeDesc(pageRequest)
    }
}