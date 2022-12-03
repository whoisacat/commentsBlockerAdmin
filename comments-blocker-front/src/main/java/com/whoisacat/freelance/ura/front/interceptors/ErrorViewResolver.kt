package com.whoisacat.freelance.ura.front.interceptors

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@Component
class ErrorViewResolver : ErrorViewResolver {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    override fun resolveErrorView(
        request: HttpServletRequest,
        status: HttpStatus,
        model: MutableMap<String, Any>
    ): ModelAndView {
        return if (HttpStatus.NOT_FOUND == status) {
            logger.error("error 404 for url " + model["path"])
            ModelAndView("error404", model)
        } else {
            ModelAndView("error", model)
        }
    }
}