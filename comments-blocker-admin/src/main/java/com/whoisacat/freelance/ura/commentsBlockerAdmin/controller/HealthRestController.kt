package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller

import com.whoisacat.freelance.ura.commentsBlockerAdmin.health.HeartbeatHealthIndicator
import org.springframework.boot.actuate.health.Status
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthRestController(private val service: HeartbeatHealthIndicator) {

    @GetMapping("/up")
    fun checkState() : ResponseEntity<Void> {
        return when (service.health().status) {
            Status.DOWN, Status.OUT_OF_SERVICE -> ResponseEntity.badRequest().build()
            else -> ResponseEntity.noContent().build()
        }
    }
}