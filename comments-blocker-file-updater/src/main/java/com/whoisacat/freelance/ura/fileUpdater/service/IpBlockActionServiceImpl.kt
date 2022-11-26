package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.dto.Action
import com.whoisacat.freelance.ura.fileUpdater.domain.IpBlockAction
import com.whoisacat.freelance.ura.fileUpdater.repository.IpBlockActionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IpBlockActionServiceImpl(private val repository: IpBlockActionRepository)
    : IpBlockActionService {

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

    @Transactional(readOnly = true)
    override fun getActivePage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.findAllByIsActiveIsTrueOrderByStartTimeDesc(pageRequest)
    }

    @Transactional(readOnly = true)
    override fun getNotSynchronizedPage(pageRequest: PageRequest, act: Action): Page<IpBlockAction> {
        return when (act) {
            Action.ADD ->  repository
                .getPageByIsSynchronizedIsFalseAndEndTimeIsNullOrderByStartTimeDesc(pageRequest)
            Action.REMOVE -> repository
                .getPageByIsSynchronizedIsFalseAndEndTimeIsNotNullOrderByStartTimeDesc(pageRequest)
        }
    }

    @Transactional(readOnly = true)
    override fun getNotActivePage(pageRequest: Pageable): Page<IpBlockAction> {
        return repository.getAllByOrderByStartTimeDesc(pageRequest)
    }
}