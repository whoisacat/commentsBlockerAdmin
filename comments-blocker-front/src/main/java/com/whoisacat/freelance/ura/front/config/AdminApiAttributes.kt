package com.whoisacat.freelance.ura.front.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "com.whoisacat.comments-blocker.admin-api-attributes")
class AdminApiAttributes {
    lateinit var protocol: String
    lateinit var host: String
    lateinit var port: String
    lateinit var redirectProtocol: String
    lateinit var redirectHost: String
    lateinit var redirectPort: String
    lateinit var frontProtocol: String
    lateinit var frontHost: String
    lateinit var frontPort: String
}
