package com.stampcrush.backend.config.interceptor;

import com.stampcrush.backend.exception.UnAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {

    private static final String BASIC_TYPE = "basic";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.toLowerCase().startsWith(BASIC_TYPE)) {
            throw new UnAuthorizationException("인증을 할 수 없습니다");
        }
        return true;
    }
}
