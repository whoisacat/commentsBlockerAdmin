package com.whoisacat.freelance.ura.commentsBlockerAdmin

import com.whoisacat.freelance.ura.commentsBlockerAdmin.interceptors.RestTemplateErrorResponseInterceptor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestTemplate
import java.util.ArrayList

@ExtendWith(SpringExtension::class)
//@Import(RestTemplate::class, RestTemplateErrorResponseInterceptor::class)
class RestTemplateItegrationTest {

    var restTemplate: RestTemplate = restTemplate()
    @Test
    fun givenRestTemplate_whenRequested_thenLogAndModifyResponse() {
        val loginForm = LoginForm("username", "password")
        val requestEntity: HttpEntity<LoginForm> = HttpEntity<LoginForm>(loginForm)
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        val responseEntity = restTemplate.postForEntity(
            "http://httpbin.org/post", requestEntity, String::class.java
        )
        Assertions.assertEquals(responseEntity.statusCode, HttpStatus.OK)
//        Assertions.assertEquals("*****",
//            responseEntity.headers["Server"]!!.get(0) ?: "") todo fix
//        Assertions.assertEquals(5,
//            responseEntity.headers["Server"]!!.get(0).length ?: 0)
    }

    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        var interceptors = restTemplate.interceptors
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = ArrayList()
        }
        interceptors.add(RestTemplateErrorResponseInterceptor())
        restTemplate.interceptors = interceptors
        return restTemplate
    }
}

class LoginForm(var username: String, var password: String) {

}
