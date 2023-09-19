package com.stampcrush.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("requestMethod", request.getMethod());
        MDC.put("requestUrl", request.getRequestURI());

        String authorization = request.getHeader("authorization");
        MDC.put("authorization", authorization);
        if (authorization == null) {
            MDC.put("authorization", "null");
        }
        filterChain.doFilter(request, response);
    }
}
