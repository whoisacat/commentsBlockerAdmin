package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller

import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.IpRecordInfoDTO
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.IpRecordService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserSettingsService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class IpRecordsHistoryController(private val service: IpRecordService,
    private val userSettingsService: UserSettingsService) {

    @GetMapping("/history")
    fun findPage(model: Model,
        @RequestParam(name = "search_text", required = false) text: String?,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int = 0): String? {
        val dtos: Page<IpRecordInfoDTO> =
            service.getActionsPage(PageRequest.of(page,
                userSettingsService.getUserSettings().rowsPerPage), text)
        model.addAttribute("ips", dtos)
        return "ip_records_history"
    }

}