package com.whoisacat.freelance.ura.commentsBlockerAdmin.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix = "com.whoisacat.comments-blocker.api-attributes")
class ApiAttributes() {

    lateinit var frontProtocol: String
    lateinit var frontHost: String
    lateinit var frontPort: String
}
