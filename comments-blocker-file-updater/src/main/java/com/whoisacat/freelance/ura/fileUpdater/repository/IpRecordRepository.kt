package com.whoisacat.freelance.ura.fileUpdater.repository

import com.whoisacat.freelance.ura.fileUpdater.domain.IpRecord
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.repository.CrudRepository

@ConditionalOnProperty(value = ["com.whoisacat.commentsBlocker.service.use"], havingValue = "db")
interface IpRecordRepository : CrudRepository<IpRecord, Long?> {
    override fun deleteById(id: Long)
    fun getOneByIp(ip: String): IpRecord?
    fun getOneById(id: Long): IpRecord?
}