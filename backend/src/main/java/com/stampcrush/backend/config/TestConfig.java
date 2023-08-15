package com.stampcrush.backend.config;

import com.stampcrush.backend.config.interceptor.BasicAuthInterceptor;
import com.stampcrush.backend.config.resolver.CustomerArgumentResolver;
import com.stampcrush.backend.config.resolver.OwnerArgumentResolver;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@Profile("test")
public class TestConfig implements WebMvcConfigurer {

    private final BasicAuthInterceptor basicAuthInterceptor;
    private final OwnerRepository ownerRepository;
    private final RegisterCustomerRepository registerCustomerRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/swagger-ui/**",
                        "/api/docs/**",
                        "/api/admin/login/**",
                        "/api/login/**"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new OwnerArgumentResolver(ownerRepository));
        resolvers.add(new CustomerArgumentResolver(registerCustomerRepository));
    }
}
