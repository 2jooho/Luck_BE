package com.example.luck_project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://192.168.219.100:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization"); // CORS로 인해 프론트단에서 인식하지 못하는 Authrization 헤더를 노출
        config.addExposedHeader("RefreshToken"); // CORS로 인해 프론트단에서 인식하지 못하는 RefreshToken 헤더를 노출

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
