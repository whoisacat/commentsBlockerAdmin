package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpRecord
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.IpRecordInfoDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

interface IpRecordService {
    fun findByIpOrCreate(authorString: String): IpRecord
    fun update(ipRecord: IpRecord): IpRecord
    fun findList(pageRequest: PageRequest, text: String?): Page<IpRecordInfoDTO>
    fun getOneByIp(ip: String): IpRecord?
    fun getOneById(id: Long): IpRecord?
    fun save(record: IpRecord): IpRecord
    fun getActionsPage(pageRequest: PageRequest, text: String?): Page<IpRecordInfoDTO>
}