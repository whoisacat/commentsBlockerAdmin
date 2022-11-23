package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.domain.IpRecord
import com.whoisacat.freelance.ura.fileUpdater.repository.IpRecordRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@ConditionalOnProperty(value = ["com.whoisacat.commentsBlocker.service.use"], havingValue = "db")
class IpRecordServiceImpl(private val repository: IpRecordRepository) : IpRecordService {

    @Transactional(readOnly = true)
    override fun getOneByIp(ip: String): IpRecord? {
        return repository.getOneByIp(ip)
    }

    @Transactional(readOnly = true)
    override fun getOneById(id: Long): IpRecord? {
        return repository.getOneById(id)
    }

    @Transactional
    override fun save(record: IpRecord): IpRecord {
        return repository.save(record)
    }
}