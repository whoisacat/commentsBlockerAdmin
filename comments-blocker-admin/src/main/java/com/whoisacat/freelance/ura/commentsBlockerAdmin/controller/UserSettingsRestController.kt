package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.UserSettings
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserSettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class UserSettingsRestController @Autowired constructor(private val service: UserSettingsService) {
    @GetMapping("userSettings")
    fun getPSettings() : ResponseEntity<UserSettings> {
        return ResponseEntity.ok(service.getUserSettings())
    }

    @PostMapping(path = ["userSettings"])
    fun setPSettings(@RequestParam(name = "rowsPerPage") rowsPerPage: Int): ResponseEntity<Void?> {
        service.setRowsPerPage(rowsPerPage)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}