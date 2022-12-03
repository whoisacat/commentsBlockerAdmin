package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller.rest

import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.IpRecordService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserSettingsService
import com.whoisacat.freelance.ura.dto.IpRecordInfoDTOPageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class IpRecordsHistoryRestController(private val service: IpRecordService,
                                     private val userSettingsService: UserSettingsService) {

    @GetMapping("/api/history")
    fun findPage(model: Model,
        @RequestParam(name = "search_text", required = false) text: String?,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int = 0)
    : ResponseEntity<IpRecordInfoDTOPageImpl> {
        return ResponseEntity.of(
            Optional.of(service.getActionsPage(PageRequest.of(page,
                userSettingsService.getUserSettings().rowsPerPage), text?.trim())))
    }
}