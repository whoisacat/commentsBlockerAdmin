package com.whoisacat.freelance.ura.front.controller

import com.whoisacat.freelance.ura.dto.IpRecordInfoDTO
import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.springframework.data.domain.Page
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate

@Controller
class IpRecordsHistoryController(restTemplate: RestTemplate,
                                 adminApiAttributes: AdminApiAttributes
) : AIpRecordsController(restTemplate, adminApiAttributes) {

    @GetMapping("/history")
    fun findPage(model: Model, @RequestHeader headers: HttpHeaders,
                 @RequestParam(name = "search_text", required = false) text: String?,
                 @RequestParam(name = "page", required = false, defaultValue = "0") page: Int = 0): String? {
        val requestEntity = HttpEntity<Void>(headers)
        val ips: Page<IpRecordInfoDTO> = getIpRecordInfoDTOS(page, text?.trim(), requestEntity,"history")
        model.addAttribute("ips", ips)
        return "ip_records_history"
    }

}