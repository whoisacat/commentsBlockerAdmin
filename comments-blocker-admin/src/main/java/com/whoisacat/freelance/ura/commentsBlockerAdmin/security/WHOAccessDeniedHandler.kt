package com.whoisacat.freelance.ura.commentsBlockerAdmin.security

import com.whoisacat.freelance.ura.commentsBlockerAdmin.config.ApiAttributes
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WHOAccessDeniedHandler(private val apiAttributes: ApiAttributes)
    : AccessDeniedHandler {

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.sendRedirect("${apiAttributes.frontProtocol}://${apiAttributes.frontHost}" +
                ":${apiAttributes.frontPort}/login")
    }
}