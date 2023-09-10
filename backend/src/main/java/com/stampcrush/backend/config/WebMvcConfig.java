package com.stampcrush.backend.config;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.config.interceptor.BasicAuthInterceptor;
import com.stampcrush.backend.config.resolver.CustomerArgumentResolver;
import com.stampcrush.backend.config.resolver.OwnerArgumentResolver;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final BasicAuthInterceptor basicAuthInterceptor;
    private final OwnerRepository ownerRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final CustomerRepository customerRepository;
    private final CafeRepository cafeRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/swagger-ui/**",
                        "/api/docs/**",
                        "/api/admin/login/**",
                        "/api/login/**",
                        "/api/admin/images"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new OwnerArgumentResolver(ownerRepository, cafeRepository, authTokensGenerator));
        resolvers.add(new CustomerArgumentResolver(customerRepository, authTokensGenerator));
    }
}
