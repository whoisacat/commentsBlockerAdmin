package com.whoisacat.freelance.ura.front.controller

import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController(private val adminApiAttributes: AdminApiAttributes) {

    @GetMapping("/logout")
    fun logout(): String {
        return "redirect:${adminApiAttributes.redirectProtocol}://${adminApiAttributes.redirectHost}" +
                ":${adminApiAttributes.redirectPort}/logout"
    }
}