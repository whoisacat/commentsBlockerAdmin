package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.domain.IpRecord
import com.whoisacat.freelance.ura.fileUpdater.repository.IpRecordRepository
import org.springframework.stereotype.Service

@Service
class IpRecordServiceImpl(private val repository: IpRecordRepository,
    private val blockActionService: IpBlockActionService) : IpRecordService {

    override fun getOneByIp(ip: String): IpRecord? {
        return repository.getOneByIp(ip)
    }

    override fun getOneById(id: Long): IpRecord? {
        return repository.getOneById(id)
    }

    override fun save(record: IpRecord): IpRecord {
        return repository.save(record)
    }
}