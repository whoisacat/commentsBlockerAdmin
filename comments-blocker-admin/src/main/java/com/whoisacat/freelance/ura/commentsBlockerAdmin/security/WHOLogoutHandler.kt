package com.whoisacat.freelance.ura.commentsBlockerAdmin.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WHOLogoutHandler : LogoutSuccessHandler, LogoutHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)
    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        log.debug("logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)")
    }

    @Throws(IOException::class, ServletException::class)
    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse,
                                 authentication: Authentication) {
        response.status = HttpServletResponse.SC_NO_CONTENT
        response.writer.flush()
    }
}
