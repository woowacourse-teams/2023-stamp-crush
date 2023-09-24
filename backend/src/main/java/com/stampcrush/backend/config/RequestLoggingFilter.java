package com.stampcrush.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final ThreadPoolTaskExecutor executor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("thread-id : {}, thread-name: {}, pool-size: {}", Thread.currentThread().getId(), Thread.currentThread().getName(), executor.getPoolSize());
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
