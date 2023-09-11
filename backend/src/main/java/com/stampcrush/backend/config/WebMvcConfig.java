package com.stampcrush.backend.config;

import com.stampcrush.backend.config.interceptor.CustomerAuthInterceptor;
import com.stampcrush.backend.config.interceptor.OwnerAuthInterceptor;
import com.stampcrush.backend.config.resolver.CustomerAuthArgumentResolver;
import com.stampcrush.backend.config.resolver.OwnerAuthArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //    private final BasicAuthInterceptor basicAuthInterceptor;
    private final OwnerAuthInterceptor ownerAuthInterceptor;
    private final CustomerAuthInterceptor customerAuthInterceptor;

    private final OwnerAuthArgumentResolver ownerAuthArgumentResolver;
    private final CustomerAuthArgumentResolver customerAuthArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(basicAuthInterceptor)
//                .addPathPatterns("/api/**")
//                .excludePathPatterns(
//                        "/api/swagger-ui/**",
//                        "/api/docs/**",
//                        "/api/admin/login/**",
//                        "/api/login/**",
//                        "/api/admin/images"
//                );
        registry.addInterceptor(ownerAuthInterceptor)
                .addPathPatterns("/api/admin/**")
                .excludePathPatterns("/api/admin/login/**");

        registry.addInterceptor(customerAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/admin/**")
                .excludePathPatterns("/api/login/**")
                .excludePathPatterns("/api/swagger-ui/**")
                .excludePathPatterns("/api/docs/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(ownerAuthArgumentResolver);
        resolvers.add(customerAuthArgumentResolver);
    }
}
