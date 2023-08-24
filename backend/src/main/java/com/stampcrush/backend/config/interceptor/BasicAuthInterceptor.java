package com.stampcrush.backend.config.interceptor;

import com.stampcrush.backend.exception.UnAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {

    private static final String BASIC_AUTHORIZATION_HEADER = "basic";
    private static final String TOKEN_AUTHORIZATION_HEADER = "bearer";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getRequestURI();

        //TODO: 나중에 삭제해야함.
        if (requestPath.startsWith("/admin/login/auth/") || requestPath.startsWith("/api/admin/login/kakao")
                || requestPath.startsWith("/api/login/kakao") || requestPath.startsWith("/login/auth/kakao")) {
            return true;
        }

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        boolean isAuthorizationNull = authorization == null;
        if (isAuthorizationNull) {
            throw new UnAuthorizationException("인증을 할 수 없습니다");
        }

        if (!isValidAuthorizationHeaderType(authorization)) {
            throw new UnAuthorizationException("인증을 할 수 없습니다");
        }

        return true;
    }

    private boolean isValidAuthorizationHeaderType(String authorization) {
        boolean isBasicAuthorization = authorization.toLowerCase().startsWith(BASIC_AUTHORIZATION_HEADER);
        boolean isTokenAuthorization = authorization.toLowerCase().startsWith(TOKEN_AUTHORIZATION_HEADER);

        return isBasicAuthorization || isTokenAuthorization;
    }
}
