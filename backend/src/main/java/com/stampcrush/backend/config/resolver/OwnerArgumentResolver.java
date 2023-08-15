package com.stampcrush.backend.config.resolver;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.OwnerUnAuthorizationException;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
public class OwnerArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC_TYPE = "Basic";
    private static final String BEARER_TYPE = "Bearer";
    private static final String DELIMITER = ":";

    private final OwnerRepository ownerRepository;
    private final AuthTokensGenerator authTokensGenerator;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(OwnerAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        // basic
        if (authorization.startsWith(BASIC_TYPE)) {
            String[] credentials = getCredentials(authorization);

            String loginId = credentials[0];
            // 비밀번호 암호화는 어디서 해야하는지 ..
            String encryptedPassword = credentials[1];

            Owner owner = ownerRepository.findByLoginId(loginId).orElseThrow(() -> new OwnerUnAuthorizationException("회원정보가 잘못되었습니다."));
            owner.checkPassword(encryptedPassword);

            return new OwnerAuth(owner.getId());
        }

        // bearer
        if (authorization.startsWith(BEARER_TYPE)) {
            String jwtToken = authorization.substring(7);
            Long ownerId = authTokensGenerator.extractMemberId(jwtToken);
            Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerUnAuthorizationException("회원정보가 잘못되었습니다."));

            return new OwnerAuth(owner.getId());
        }

        throw new AuthenticationException();
    }

    private String[] getCredentials(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();

        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        return decodedString.split(DELIMITER);
    }
}
