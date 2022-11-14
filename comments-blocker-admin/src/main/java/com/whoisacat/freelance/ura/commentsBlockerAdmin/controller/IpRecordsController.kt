package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.ROLES
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.IpRecordInfoDTO
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.MainPageDTO
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.IpRecordService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserSettingsService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.UserNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class IpRecordsController(private val service: IpRecordService,
    private val userSettingsService: UserSettingsService) {

    @GetMapping("/")
    fun findPage(model: Model,
        @RequestParam(name = "search_text", required = false) text: String?,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int = 0): String? {
        val ips: Page<IpRecordInfoDTO> =
            service.findList(PageRequest.of(page,
                userSettingsService.getUserSettings().rowsPerPage), text)
        val isAdmin = userSettingsService.getUserSettings().user
            ?.run {
                roles.stream().anyMatch { ROLES.ROLE_ADMIN == it.roleName }
            } ?: false
        val dto = MainPageDTO(isAdmin, ips)
        model.addAttribute("dto", dto)
        return "ip_records"
    }

}