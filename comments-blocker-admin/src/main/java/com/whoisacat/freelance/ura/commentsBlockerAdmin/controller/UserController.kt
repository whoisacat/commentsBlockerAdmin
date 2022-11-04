package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller

import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController(private val userService: UserService) {
    @GetMapping("user/mememe")
    fun getUserInfo(model: Model): String {
        val editUserDTO = userService.getEditUserDTO()
        model.addAttribute("dto", editUserDTO)
        return "mememe"
    }
}