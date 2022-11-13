package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.domain.IpBlockAction
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

interface IpBlockActionService {
    fun findActivePageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction>
    fun findPageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction>
    fun save(action: IpBlockAction): IpBlockAction
    fun getActivePage(pageRequest: Pageable): Page<IpBlockAction>
    fun getNotActivePage(pageRequest: Pageable): Page<IpBlockAction>
    fun getNotSynchronizedPage(pageSize: PageRequest, add: Action): Page<IpBlockAction>
}
