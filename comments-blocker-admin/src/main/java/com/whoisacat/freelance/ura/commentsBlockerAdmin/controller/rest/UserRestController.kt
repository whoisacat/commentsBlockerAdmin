package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller.rest

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.Role
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserService
import com.whoisacat.freelance.ura.domain.ROLES
import com.whoisacat.freelance.ura.dto.BooleanDTO
import com.whoisacat.freelance.ura.dto.EditUserDTO
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.function.Predicate

@RestController
class UserRestController(private val userService: UserService) {
    @GetMapping("api/user/mememe")
    fun getUserInfo(model: Model): ResponseEntity<EditUserDTO> {
        return ResponseEntity.ok(userService.getEditUserDTO())
    }

    @GetMapping("api/user/isAdmin")
    fun isUserAdmin(model: Model): ResponseEntity<BooleanDTO> {
        return ResponseEntity.ok(BooleanDTO(userService.getCurrentUser().roles
            .stream()
            .map(Role::roleName)
            .filter(Predicate.isEqual(ROLES.ROLE_ADMIN))
            .findAny()
            .isPresent))
    }
}