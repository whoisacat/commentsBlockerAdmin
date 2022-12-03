package com.whoisacat.freelance.ura.front.controller.rest

import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate


@RestController
class LoginRestController(private val restTemplate: RestTemplate, private val adminApiAttributes: AdminApiAttributes) {
    @GetMapping("/login")
    fun showRegistrationForm(): String {
        return restTemplate.exchange("${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                ":${adminApiAttributes.port}/login",
            HttpMethod.GET, HttpEntity.EMPTY, String::class.java).body?:"unknown error"
    }

    @PostMapping("/login")
    fun login(sensitive: Sensitive, @RequestHeader headers: HttpHeaders): ResponseEntity<Void> {

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("username", sensitive.username)
        body.add("password", sensitive.password)
        val requestEntity = HttpEntity(body, headers)
        val response = restTemplate.exchange("${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                ":${adminApiAttributes.port}/login", HttpMethod.POST, requestEntity, Void::class.java
        )
        return ResponseEntity.status(response.statusCode).headers(response.headers).build()
    }

    data class Sensitive(val username: String, val password: String)
}