package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller.rest

import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.IpRecordService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserSettingsService
import com.whoisacat.freelance.ura.dto.IpRecordInfoDTOPageImpl
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class IpRecordsRestController(private val service: IpRecordService,
                              private val userSettingsService: UserSettingsService) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/api/ipRecords")
    fun findPage(@RequestParam(name = "search_text", required = false) text: String?,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int = 0)
    : ResponseEntity<IpRecordInfoDTOPageImpl> {
        log.error("search_text == {} ", text)
        return ResponseEntity.of(Optional.of(service.findList(PageRequest.of(page,
                userSettingsService.getUserSettings().rowsPerPage), text?.trim())))
    }
}