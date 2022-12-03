package com.whoisacat.freelance.ura.commentsBlockerAdmin.interceptors

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(RuntimeException::class)
    fun runtimeException(e: RuntimeException): ResponseEntity<String> {
        logger.error(e.message, e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }
}