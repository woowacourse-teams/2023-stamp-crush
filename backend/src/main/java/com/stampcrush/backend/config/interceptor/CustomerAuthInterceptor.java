package com.stampcrush.backend.config.interceptor;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.BasicParser;
import com.stampcrush.backend.auth.application.util.BearerParser;
import com.stampcrush.backend.exception.UnAuthorizationException;
import com.stampcrush.backend.repository.user.CustomerRepository;
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
    private final AuthTokensGenerator authTokensGenerator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (BasicParser.isBasicAuthType(authorization)) {
            return true;
        }

        if (!BearerParser.isBearerAuthType(authorization)) {
            throw new UnAuthorizationException("인증할 수 없습니다.");
        }

        String accessToken = BasicParser.parseAuthorization(authorization);
        if (!authTokensGenerator.isValidToken(accessToken)) {
            throw new UnAuthorizationException("인증할 수 없습니다.");
        }

        Long customerId = authTokensGenerator.extractMemberId(accessToken);
        customerRepository.findById(customerId)
                .orElseThrow(() -> new UnAuthorizationException("인증할 수 없습니다."));
        return true;
    }
}
