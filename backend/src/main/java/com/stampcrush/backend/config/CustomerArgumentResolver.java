package com.stampcrush.backend.config;

import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.exception.CustomerUnAuthorizationException;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC_TYPE = "basic";
    private static final String DELIMITER = ":";

    private final RegisterCustomerRepository customerRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(RegisterCustomer.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.toLowerCase().startsWith(BASIC_TYPE)) {
            return null;
        }

        String[] credentials = getCredentials(authorization);

        String loginId = credentials[0];
        String encryptedPassword = credentials[1];

        RegisterCustomer customer = customerRepository.findByLoginId(loginId).orElseThrow(() -> new CustomerUnAuthorizationException("스탬프크러쉬 가입 후 사용가능합니다"));
        customer.checkPassword(encryptedPassword);

        return customer;
    }

    private String[] getCredentials(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();

        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        return decodedString.split(DELIMITER);
    }
}
