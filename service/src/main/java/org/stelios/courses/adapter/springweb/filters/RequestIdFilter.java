package org.stelios.courses.adapter.springweb.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID_HEADER = "RequestId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestIdHeaderValue = getHeaderValue(httpRequest, REQUEST_ID_HEADER);

        String requestId = getValidRequestId(requestIdHeaderValue);

        MDC.put(REQUEST_ID_HEADER, requestId);

        ((HttpServletResponse) response).setHeader(REQUEST_ID_HEADER, requestId);
        log.info(REQUEST_ID_HEADER + " MDC value is [{}]. Response Header value is [{}]", MDC.get(REQUEST_ID_HEADER), requestId);

        chain.doFilter(httpRequest, response);

        if (HttpStatus.valueOf(((HttpServletResponse) response).getStatus()).isError()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }

    public String getHeaderValue(HttpServletRequest request, String headerNameRequested) {
        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        Collections.list(request.getHeaderNames())
                .forEach(headerName -> headers.put(headerName, request.getHeader(headerName)));

        return headers.get(headerNameRequested.toLowerCase());
    }

    private static String getValidRequestId(String uuid) {
        try {
            return isBlank(uuid) ? randomUUID().toString() : UUID.fromString(uuid).toString();
        } catch (IllegalArgumentException e) {
            return UUID.randomUUID().toString();
        }
    }
}
