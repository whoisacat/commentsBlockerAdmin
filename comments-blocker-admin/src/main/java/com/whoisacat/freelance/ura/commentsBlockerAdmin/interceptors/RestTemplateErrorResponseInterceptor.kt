package com.whoisacat.freelance.ura.commentsBlockerAdmin.interceptors

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.util.StreamUtils
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.text.MessageFormat

class RestTemplateErrorResponseInterceptor : ClientHttpRequestInterceptor {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        traceRequest(request, body)
        val response : ClientHttpResponse = execution.execute(request, body)
        traceResponce(response)
        return response
    }

    private fun traceRequest(request: HttpRequest, body: ByteArray) {
        val sb: StringBuilder = StringBuilder()
        sb.append("\n------------request begin------------\n")
        sb.append(MessageFormat.format("URI             : {0}\n", request.uri))
        sb.append(MessageFormat.format("Method          : {0}\n", request.methodValue))
        sb.append(MessageFormat.format("Headers         : {0}\n", request.headers))
        sb.append(MessageFormat.format("Body            : {0}\n", String(body, Charset.forName("UTF-8"))))
        sb.append("-------------request end-------------")
        logger.debug(sb.toString())
    }

    private fun traceResponce(response: ClientHttpResponse) {
        val sb: StringBuilder = StringBuilder()
        if (response.statusCode.isError) {
            sb.append("\n------------error response begin------------\n")
        } else {
            sb.append("\n------------response begin------------\n")
        }
        sb.append(MessageFormat.format("Status code     : {0}\n", response.statusCode))
        sb.append(MessageFormat.format("Status text     : {0}\n", response.statusText))
        sb.append(MessageFormat.format("Headers         : {0}\n", clearHttpHeadersFromSensitive(response)))
        var bodyString = clearHttpBodyFromSensitive(StreamUtils.copyToString(response.body, Charset.forName("UTF-8")))
        sb.append(MessageFormat.format("Response body   : {0}\n",
            bodyString
                .replace("\n","")))
        sb.append("-------------response end-------------")
        logger.debug(sb.toString())
    }

    private fun clearHttpBodyFromSensitive(bodyString: String): String {
        sensitiveBodyFields.forEach {

        }

        return ""
    }

    private fun clearHttpHeadersFromSensitive(response: ClientHttpResponse): String {
        val headers = response.headers
        var headersString =headers.toString()
        sensitiveHeaderFields.forEach {
            val sensitive = headers.get(it) ?: emptyList()
            if (sensitive.size > 0) {
                sensitive.forEach {s ->
                    headersString = headersString.replace(s, "*****")
                }
            }
        }
        return headersString
    }

    companion object {
        val sensitiveHeaderFields = mutableListOf("Server", "Connection")
        val sensitiveBodyFields = mutableListOf("origin", "Content-Type")
        val sensitiveBodyArrays = mutableListOf("json", "data")
    }
}