package com.whoisacat.freelance.ura.fileUpdater.repository

import com.whoisacat.freelance.ura.fileUpdater.domain.IpRecord
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface IpRecordRepository : CrudRepository<IpRecord, Long?> {
    override fun deleteById(id: Long)
    fun getOneByIp(ip: String): IpRecord?
    fun getOneById(id: Long): IpRecord?
}