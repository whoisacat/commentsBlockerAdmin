package com.whoisacat.freelance.ura.front.interceptors

import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException


@Aspect
@Component
class UnauthorizedAspect(private val adminApiAttributes: AdminApiAttributes) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Around("execution(* com.whoisacat.freelance.ura.front.controller.*Controller.*(..))")
    fun redirectToLoginPageAroundAdvice(pjp: ProceedingJoinPoint): String {
        var response: String = "error"
        try {
            response = pjp.proceed() as String
        } catch (ex: HttpClientErrorException) {
            if (ex.statusCode == HttpStatus.UNAUTHORIZED) {
                log.debug("ex.statusCode == {}",HttpStatus.UNAUTHORIZED)
                return "redirect:${adminApiAttributes.frontProtocol}://${adminApiAttributes.frontHost}" +
                        ":${adminApiAttributes.frontPort}/login"
            }
            throw ex
        } catch (ex: ClassCastException) {
            log.error(ex.message, ex)
        }
        return response
    }
}