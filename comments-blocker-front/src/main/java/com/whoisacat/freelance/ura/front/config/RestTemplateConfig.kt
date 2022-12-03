package com.whoisacat.freelance.ura.front.config

import com.whoisacat.freelance.ura.front.interceptors.RequestResponseLoggingInterceptorJava
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    private val log = LoggerFactory.getLogger(RequestResponseLoggingInterceptorJava::class.java)
    @Bean
    fun restTemplate(): RestTemplate? {
        val restTemplate = when (log.isDebugEnabled) {
            true -> {
                val factory: ClientHttpRequestFactory =
                    BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory())
                RestTemplate(factory)}
            false -> RestTemplate()
        }
        var interceptors = restTemplate.interceptors
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = ArrayList()
        }
        interceptors.add(RequestResponseLoggingInterceptorJava())
        restTemplate.interceptors = interceptors
        return restTemplate
    }
}