package com.whoisacat.freelance.ura.commentsBlockerAdmin.validation

import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class IpValidator {

    fun isValid(ip: String): Boolean {
        return validateIp(ip)
    }

    private fun validateIp(ip: String): Boolean {
        if (ip.length < 6 || ip.length > 16) {
            return false
        }
        val pattern =
            Pattern.compile(IP_PATTERN)
        val matcher = pattern.matcher(ip)
        return matcher.matches()
    }

    companion object {
        private const val IP_PATTERN = ("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")
    }
}