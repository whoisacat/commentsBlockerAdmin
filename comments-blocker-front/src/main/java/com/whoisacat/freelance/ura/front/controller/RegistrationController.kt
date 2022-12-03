package com.whoisacat.freelance.ura.front.controller

import com.whoisacat.freelance.ura.dto.UserRegistrationDTO
import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
class RegistrationController(private val restTemplate: RestTemplate,
                             private val adminApiAttributes: AdminApiAttributes
) {
    @GetMapping("/user/registration")
    fun showRegistrationForm(request: WebRequest?, model: Model): String {
        val userRegistrationDto = UserRegistrationDTO()
        model.addAttribute("user", userRegistrationDto)
        return "registration"
    }

    @PostMapping("/user/registration")
    fun registerUserAccount(model: Model,
        @ModelAttribute("user") userRegistrationDto: @Valid UserRegistrationDTO,
        @RequestHeader headers: HttpHeaders,
        request: HttpServletRequest?
    ): String {
        try {
            headers.contentType = MediaType.APPLICATION_JSON
            val requestEntity = HttpEntity<UserRegistrationDTO>(userRegistrationDto, headers)
            restTemplate
                .exchange("${adminApiAttributes.protocol}://${adminApiAttributes.host}" +
                        ":${adminApiAttributes.port}/api/user/registration", HttpMethod.POST,
                    requestEntity, Void::class.java)
        } catch (ex: Exception) {
            model.addAttribute("message", "An account for ${userRegistrationDto.email}" +
                    " username/email already exists")
            return "emailError"
        }
        model.addAttribute("user", userRegistrationDto)
        return "successRegister"
    }
}