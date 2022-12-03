package com.whoisacat.freelance.ura.front.controller

import com.whoisacat.freelance.ura.exceptions.IpIsInvalidRequestException
import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import com.whoisacat.freelance.ura.front.validation.IpValidator
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate

@Controller
class BlockIpController(
    private val recordsController: IpRecordsController,
    private val ipValidator: IpValidator,
    private val restTemplate: RestTemplate,
    private val adminApiAttributes: AdminApiAttributes
) {

    @GetMapping("/block/{action}")
    fun block(model: Model, @RequestHeader headers: HttpHeaders,
              @RequestParam(name = "ip", required = true) blockedIp: String = "",
              @RequestParam(name = "blockPeriod", required = true) blockPeriod: String
    ): String? {
        val ip = blockedIp.trim()
        if (!ipValidator.isValid(ip)) throw IpIsInvalidRequestException(ip)
        val requestEntity = HttpEntity<Void>(headers)
        restTemplate.exchange("${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                ":${adminApiAttributes.port}/api/block/$ip/on?blockPeriod=$blockPeriod",
            HttpMethod.POST, requestEntity, Void::class.java)
        return recordsController.findPage(model, requestEntity.headers, ip)
    }
    @GetMapping("/block/off")
    fun unblock(model: Model, @RequestHeader headers: HttpHeaders,
                @RequestParam(name = "id", required = true) id: Long
    ): String? {
        val requestEntity = HttpEntity<Void>(headers)
        restTemplate.exchange("${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                ":${adminApiAttributes.port}/api/block/off/?id=$id",
            HttpMethod.POST, requestEntity, Void::class.java)
        return recordsController.findPage(model = model, headers = headers, "")
    }
}
