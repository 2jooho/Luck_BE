package com.example.luck_project.common.config;

import com.example.luck_project.common.interceptor.ApiAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {


    /** Interceptor 추가 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* 모든 요청에 적용 */
        registry.addInterceptor(new ApiAuthInterceptor()).addPathPatterns("/**");
    }


}