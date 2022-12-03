package com.whoisacat.freelance.ura.front.controller

import com.whoisacat.freelance.ura.dto.EditUserDTO
import com.whoisacat.freelance.ura.exceptions.WHOClientRequestException
import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.client.RestTemplate

@Controller
class UserController(private val restTemplate: RestTemplate,
                     private val adminApiAttributes: AdminApiAttributes
) {
    @GetMapping("user/mememe")
    fun getUserInfo(model: Model, requestEntity: HttpEntity<Void>): String {
        val answer = restTemplate.exchange(
            "${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                    ":${adminApiAttributes.port}/api/user/mememe",
            HttpMethod.GET, requestEntity, EditUserDTO::class.java
        )
        if (answer.statusCode != HttpStatus.OK) {
            throw WHOClientRequestException(answer.statusCode.name)
        }
        model.addAttribute("dto", answer.body)
        return "mememe"
    }
}