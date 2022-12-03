package com.whoisacat.freelance.ura.front.controller

import com.whoisacat.freelance.ura.dto.*
import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate

@Controller
class IpRecordsController(restTemplate: RestTemplate, adminApiAttributes: AdminApiAttributes)
    : AIpRecordsController(restTemplate, adminApiAttributes) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/")
    fun findPage(model: Model, @RequestHeader headers: HttpHeaders,
                 @RequestParam(name = "search_text", required = false) text: String?,
                 @RequestParam(name = "page", required = false, defaultValue = "0") page: Int = 0): String {
        log.error("search_text == {} ", text)
        val requestEntity = HttpEntity<Void>(headers)
        val ips: Page<IpRecordInfoDTO> = getIpRecordInfoDTOS(page, text, requestEntity,"ipRecords")
        val isAdmin = restTemplate.exchange("${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                ":${adminApiAttributes.port}/api/user/isAdmin", HttpMethod.GET, requestEntity,
                BooleanDTO::class.java).body?.answer?:false
        val dto = MainPageDTO(isAdmin, ips)
        model.addAttribute("dto", dto)
        return "ip_records"
    }
}
