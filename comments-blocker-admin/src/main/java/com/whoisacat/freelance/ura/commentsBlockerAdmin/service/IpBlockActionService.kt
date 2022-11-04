package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.BlockPeriod
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpBlockAction
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

interface IpBlockActionService {
    fun findPageByUser(pageRequest: PageRequest, text: String): Page<IpBlockAction>
    fun findActivePageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction>
    fun findPageByIpRecordsIds(ids: List<Long>, pageRequest: Pageable): Page<IpBlockAction>
    fun blockIp(ip: String, blockPeriod: BlockPeriod)
    fun save(action: IpBlockAction): IpBlockAction
    fun unblockIp(id: Long)
    fun getPage(pageRequest: Pageable): Page<IpBlockAction>
    fun getNotActivePage(pageRequest: Pageable): Page<IpBlockAction>
}
