package com.whoisacat.freelance.ura.commentsBlockerAdmin.interceptors;

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
import java.util.Arrays;
import java.util.List;

public class RequestResponseLoggingInterceptorJava implements ClientHttpRequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
    {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        response.getHeaders().add("headerName", "VALUE");

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("===========================request begin================================================");
            log.debug("URI         : {}", request.getURI());
            log.debug("Method      : {}", request.getMethod());
            log.debug("Headers     : {}", request.getHeaders());
            log.debug("Request body: {}", new String(body, "UTF-8"));
            log.debug("==========================request end================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("============================response begin==========================================");
            log.debug("Status code  : {}", response.getStatusCode());
            log.debug("Status text  : {}", response.getStatusText());
            log.debug("Headers      : {}", clearHttpHeadersFromSensitive(response));
            log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            log.debug("=======================response end=================================================");
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

    private final List<String> sensitiveHeaderFields = Arrays.asList("Server", "Connection");
    private final List<String>  sensitiveBodyFields = Arrays.asList("origin", "Content-Type");
    private final List<String>  sensitiveBodyArrays = Arrays.asList("json", "data");
}