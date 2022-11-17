package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpBlockAction
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.IpRecord
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.IpRecordInfoDTO
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.WHOPageImpl
import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.IpRecordRepository
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.WHODataAccessException
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.WHOIpAlreadyExists
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.StringBuilder
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors

@Service
class IpRecordServiceImpl(private val repository: IpRecordRepository,
    private val blockActionService: IpBlockActionService) : IpRecordService {

    @Transactional
    override fun findByIpOrCreate(ip: String): IpRecord {
        val genres = repository.findByIp(ip)
        if (genres.size == 1) {
            return genres[0]
        }
        if (genres.size > 1) {
            throw WHOIpAlreadyExists()
        }
        val ipRecord = IpRecord(null, ip, city = null)
        val id = repository.save(ipRecord).id ?: throw WHODataAccessException("didNtSaved")
        return repository.getById(id) ?: throw WHODataAccessException("didNtSaved")
    }

    @Transactional(readOnly = true)
    override fun findList(pageRequest: PageRequest, text: String?): Page<IpRecordInfoDTO> {
        return when {
            text != null && text.trim().isNotEmpty() -> {
                val ips = repository.findByIpContaining(text, pageRequest)
                val actions = when {
                    ips.isEmpty() -> {
                        blockActionService.findPageByUser(pageRequest, text.trim())
                    }
                    else -> {
                        val ids = ips.stream().map { it.id!! }.toList()
                        blockActionService.findActivePageByIpRecordsIds(ids, pageRequest)
                    }
                }
                WHOPageImpl<IpRecordInfoDTO>(actions.stream()
                    .map {toDTO(it)}.collect(Collectors.toList()),
                    pageRequest, actions.totalElements, text)
            }
            else -> {
                val page = blockActionService.getPage(pageRequest)
                WHOPageImpl<IpRecordInfoDTO>(page
                    .stream().map { toDTO(it) }.toList(),
                    pageRequest, page.totalElements, "")
            }
        }
    }

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

    private fun toDTO(blockAction: IpBlockAction): IpRecordInfoDTO {
        return IpRecordInfoDTO(blockActionId = blockAction.id!!,
            ip = blockAction.record.ip,
            startTime = blockAction.startTime
                .format(DateTimeFormatter
                    .ofPattern("dd MMMM yyyy", Locale.forLanguageTag("ru-RU"))),
            endTime = blockAction.endTime
                ?.format(DateTimeFormatter
                    .ofPattern("dd MMMM yyyy", Locale.forLanguageTag("ru-RU")))
                ?: "",
            country = blockAction.record.country ?: "",
            city = blockAction.record.city ?: "",
            blockPeriod = blockAction.blockPeriod,
            user = StringBuilder().append(blockAction.user.lastName).append(" ")
                .append(blockAction.user.firstName).toString(),
            userExclude = StringBuilder().append(blockAction.userExclude?.lastName?:"").append(" ")
                .append(blockAction.userExclude?.firstName?:"").toString())
    }

    @Transactional(readOnly = true)
    override fun getActionsPage(pageRequest: PageRequest, text: String?): Page<IpRecordInfoDTO> {
        return when {
            (text != null) && text.trim().isNotEmpty() -> {
                val ips = repository.findByIpContaining(text, pageRequest)
                val actions = when {
                    ips.isEmpty() -> {
                        blockActionService.findPageByUser(pageRequest, text.trim())
                    }
                    else -> {
                        val ids = ips.stream().map { it.id!! }.toList()
                        blockActionService.findPageByIpRecordsIds(ids, pageRequest)
                    }
                }
                WHOPageImpl<IpRecordInfoDTO>(actions.stream()
                    .map {toDTO(it)}.collect(Collectors.toList()),
                    pageRequest, actions.totalElements, text)
            }
            else -> {
                val page = blockActionService.getNotActivePage(pageRequest)
                WHOPageImpl<IpRecordInfoDTO>(page
                    .stream().map { toDTO(it) }.toList(),
                    pageRequest, page.totalElements, "")
            }
        }
    }

    @Transactional
    override fun update(genre: IpRecord): IpRecord {
        return repository.save(genre)
    }
}