package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpRecord
import com.whoisacat.freelance.ura.dto.IpRecordInfoDTOPageImpl
import org.springframework.data.domain.PageRequest

interface IpRecordService {
    fun findByIpOrCreate(ip: String): IpRecord
    fun update(ipRecord: IpRecord): IpRecord
    fun findList(pageRequest: PageRequest, text: String?): IpRecordInfoDTOPageImpl
    fun getOneByIp(ip: String): IpRecord?
    fun getOneById(id: Long): IpRecord?
    fun save(record: IpRecord): IpRecord
    fun getActionsPage(pageRequest: PageRequest, text: String?): IpRecordInfoDTOPageImpl
}