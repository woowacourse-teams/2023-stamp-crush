package com.stampcrush.backend.config.resolver;

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
        return parameter.getParameterType().equals(CustomerAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        String[] credentials = getCredentials(authorization);

        String loginId = credentials[0];
        String encryptedPassword = credentials[1];

        RegisterCustomer customer = customerRepository.findByLoginId(loginId).orElseThrow(() -> new CustomerUnAuthorizationException("회원정보가 잘못되었습니다."));
        customer.checkPassword(encryptedPassword);

        return new CustomerAuth(customer.getId(), customer.getNickname(), customer.getPhoneNumber(), loginId, encryptedPassword);
    }

    private String[] getCredentials(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();

        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        return decodedString.split(DELIMITER);
    }
}
