package com.stampcrush.backend.config.resolver;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.BearerParser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class OwnerAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthTokensGenerator authTokensGenerator;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(OwnerAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        String accessToken = BearerParser.parseAuthorization(authorization);
        Long customerId = authTokensGenerator.extractMemberId(accessToken);

        return new OwnerAuth(customerId);
    }
//    private void validateOwnerShipWhenQueryString(WebRequest request, Owner owner) {
//        String parameter = request.getParameter("cafe-id");
//
//        if (parameter != null) {
//            Long cafeId = Long.parseLong(parameter);
//
//            Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeNotFoundException("카페정보가 없습니다"));
//            cafe.validateOwnership(owner);
//        }
//    }
//
//    private void validateOwnerShipWhenPathVariable(HttpServletRequest request, Owner owner) {
//        String requestUri = request.getRequestURI();
//
//        String[] uriParts = requestUri.split("/");
//        Long cafeId = null;
//        for (int i = 0; i < uriParts.length; i++) {
//            if ("cafes".equals(uriParts[i]) && i + 1 < uriParts.length) {
//                cafeId = Long.parseLong(uriParts[i + 1]);
//                break;
//            }
//        }
//        if (cafeId != null) {
//            Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeNotFoundException("카페정보가 없습니다"));
//
//            cafe.validateOwnership(owner);
//        }
//    }
}
