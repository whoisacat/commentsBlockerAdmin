package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller.rest

import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.UserAlreadyExistException
import com.whoisacat.freelance.ura.dto.UserRegistrationDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Controller
class RegistrationRestController(private val userService: UserService) {

    @Throws(UserAlreadyExistException::class)
    @PostMapping("/api/user/registration")
    fun registerUserAccount(@RequestBody userRegistrationDto: @Valid UserRegistrationDTO): ResponseEntity<Void> {
        userService.registerNewUserAccount(userRegistrationDto)
        return ResponseEntity.ok().build()
    }
}
