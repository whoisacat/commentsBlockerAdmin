package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller

import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.UserRegistrationDTO
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.UserAlreadyExistException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
class RegistrationController(private val userService: UserService) {
    @GetMapping("/user/registration")
    fun showRegistrationForm(request: WebRequest?, model: Model): String {
        val userRegistrationDto = UserRegistrationDTO()
        model.addAttribute("user", userRegistrationDto)
        return "registration"
    }

    @PostMapping("/user/registration")
    fun registerUserAccount(
        @ModelAttribute("user") userRegistrationDto: @Valid UserRegistrationDTO?,
        request: HttpServletRequest?
    ): ModelAndView {
        try {

            userService.registerNewUserAccount(userRegistrationDto!!)
        } catch (uaeEx: UserAlreadyExistException) {
            val mav = ModelAndView("emailError")
            mav.addObject("message", "An account for that username/email already exists.")
            return mav
        }
        return ModelAndView("successRegister", "user", userRegistrationDto)
    }
}