package com.whoisacat.freelance.ura.front.interceptors;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class RequestResponseLoggingInterceptorJava implements ClientHttpRequestInterceptor {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body,
                                        ClientHttpRequestExecution execution)
            throws IOException {
        logRequest(request, body);
        ClientHttpResponse response;
        try {
             response = execution.execute(request, body);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        logResponse(response);
        response.getHeaders().add("headerName", "VALUE");

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("===========================request begin================================================");
            logger.debug("URI         : {}", request.getURI());
            logger.debug("Method      : {}", request.getMethod());
            logger.debug("Headers     : {}", request.getHeaders());
            logger.debug("Request body: {}", new String(body, StandardCharsets.UTF_8));
            logger.debug("==========================request end================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("============================response begin==========================================");
            logger.debug("Status code  : {}", response.getStatusCode());
            logger.debug("Status text  : {}", response.getStatusText());
            logger.debug("Headers      : {}", clearHttpHeadersFromSensitive(response));
            logger.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            logger.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            logger.debug("=======================response end=================================================");
        }
    }


    private String clearHttpBodyFromSensitive(String bodyString) {


        return bodyString;
    }

    private String clearHttpHeadersFromSensitive(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        String headersString = headers.toString();
        for(String field : sensitiveHeaderFields) {
            List<String> sensitive = headers.get(field);
            if (sensitive != null) {
                if (sensitive.size() > 0) {
                    for (String s : sensitive) {
                        headersString = headersString.replace(s, "*****");
                    }
                }
            }
        }
        return headersString;
    }

    private final List<String> sensitiveHeaderFields = Arrays.asList("Server", "cookie");
    private final List<String>  sensitiveBodyFields = Arrays.asList("origin", "Content-Type");
    private final List<String>  sensitiveBodyArrays = Arrays.asList("json", "data");
}