package com.whoisacat.freelance.ura.front.controller.rest

import com.whoisacat.freelance.ura.dto.UserSettingsDTO
import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@RestController
class UserSettingsRestController(val restTemplate: RestTemplate,
                                 private val adminApiAttributes: AdminApiAttributes
) {

    @GetMapping("userSettings")
    fun getPSettings(@RequestHeader headers: HttpHeaders): ResponseEntity<UserSettingsDTO> {
        return restTemplate.exchange("${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                ":${adminApiAttributes.port}/api/userSettings",
            HttpMethod.GET, HttpEntity<Void>(headers), UserSettingsDTO::class.java)
    }

    @PostMapping(path = ["userSettings"])
    fun setPSettings(
        @RequestHeader headers: HttpHeaders,
        @RequestParam(name = "rowsPerPage") rowsPerPage: Int
    ): ResponseEntity<Void> {
        headers.contentType = MediaType.APPLICATION_JSON
        val httpEntity = HttpEntity<UserSettingsDTO>(headers)
        return restTemplate.exchange("${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                ":${adminApiAttributes.port}/api/userSettings?" +
                "rowsPerPage=$rowsPerPage",
            HttpMethod.POST,
            httpEntity,
            Void::class.java)
    }
}