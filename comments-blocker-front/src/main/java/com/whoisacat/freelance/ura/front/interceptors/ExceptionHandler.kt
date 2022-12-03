package com.whoisacat.freelance.ura.front.interceptors

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.servlet.ModelAndView
import java.net.ConnectException


@ControllerAdvice
class ExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(HttpClientErrorException::class)
    fun httpClientErrorException(ex: HttpClientErrorException): ModelAndView {
        logger.info(ex.message)
        val model = ModelMap()
        model["exception"] = ex.message
        return ModelAndView("error400", model)
    }

    @ExceptionHandler(ConnectException::class)
    fun connectException(ex: ConnectException): ModelAndView {
        logger.info(ex.message)
        val model = ModelMap()
        return ModelAndView("no_api", model)
    }
}