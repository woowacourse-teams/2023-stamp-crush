package com.stampcrush.backend.config.interceptor;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.BearerParser;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.exception.UnAuthorizationException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class CustomerAuthInterceptor implements HandlerInterceptor {

    private final CustomerRepository customerRepository;
    private final BlackListRepository blackListRepository;
    private final AuthTokensGenerator authTokensGenerator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!BearerParser.isBearerAuthType(authorization)) {
            throw new UnAuthorizationException("인증할 수 없습니다.");
        }

        String accessToken = BearerParser.parseAuthorization(authorization);
        if (!authTokensGenerator.isValidToken(accessToken)) {

            String refreshToken = getRefreshToken(request);
            if (blackListRepository.isValidRefreshToken(refreshToken)) {
                // TODO: Access Token, Refresh Token 재발급
                Long customerId = getCustomerId(accessToken);
                AuthTokensResponse newTokens = authTokensGenerator.generate(customerId);
                request.setAttribute("Authorization", newTokens.getAccessToken());
            }
            // TODO: 인증 안됨

            throw new UnAuthorizationException("인증할 수 없습니다.");
        }

        Long customerId = getCustomerId(accessToken);
        customerRepository.findById(customerId)
                .orElseThrow(() -> new UnAuthorizationException("인증할 수 없습니다."));
        return true;
    }

    private Long getCustomerId(String accessToken) {
        return authTokensGenerator.extractMemberId(accessToken);
    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnAuthorizationException("Cookie가 존재하지 않습니다");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }
        throw new UnAuthorizationException("Refresh Token이 존재하지 않습니다");
    }
}
