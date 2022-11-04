package com.whoisacat.freelance.ura.commentsBlockerAdmin.repository

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpRecord
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface IpRecordRepository : CrudRepository<IpRecord, Long?> {
    fun getById(id: Long): IpRecord?
    fun findByIp(title: String): List<IpRecord>
    fun findByIpContaining(title: String, pageRequest: Pageable): List<IpRecord>
    override fun deleteById(id: Long)
    fun getOneByIp(ip: String): IpRecord?
    fun getOneById(id: Long): IpRecord?
}