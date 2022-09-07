package com.example.luck_project.common.config;

import com.example.luck_project.common.interceptor.ApiAuthInterceptor;
import com.example.luck_project.common.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;

//    @Autowired
//    private HpAuthMapper authMapper;


    /** Interceptor 추가 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* 모든 요청에 적용 */
        registry.addInterceptor(new ApiAuthInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new TokenCheckInterceptor(authMapper)).addPathPatterns("/**");
    }


    /** messages.properties 처리용 util */
    @Bean(name="messageUtil")
    public MessageUtil messageUtil(){
        MessageUtil messageUtil = new MessageUtil(env);
        return messageUtil;
    }

}