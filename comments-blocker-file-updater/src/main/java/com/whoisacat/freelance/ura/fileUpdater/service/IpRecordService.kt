package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.domain.IpRecord

interface IpRecordService {
    fun getOneByIp(ip: String): IpRecord?
    fun getOneById(id: Long): IpRecord?
    fun save(record: IpRecord): IpRecord
}