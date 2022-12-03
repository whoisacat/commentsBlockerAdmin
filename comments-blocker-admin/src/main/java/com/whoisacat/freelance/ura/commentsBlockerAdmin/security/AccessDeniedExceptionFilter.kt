package com.whoisacat.freelance.ura.commentsBlockerAdmin.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.whoisacat.freelance.ura.exceptions.WHOClientRequestException
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
@Component
class AccessDeniedExceptionFilter(private val mapper: ObjectMapper) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this.javaClass)
    @Throws(ServletException::class, IOException::class)
    public override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse,
        fc: FilterChain
    ) {
        try {
            fc.doFilter(request, response)
        } catch (e: AccessDeniedException) {
            log.error("access denied with request ${request.requestURI}")
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
            response.getWriter().write(mapper
                .writeValueAsString(WHOClientRequestException(e.localizedMessage)))
        }
    }
}