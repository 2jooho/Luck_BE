package com.example.luck_project.common.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket restApi() {
//        Parameter parameterBuilder = new ParameterBuilder()
//            .name(HttpHeaders.AUTHORIZATION)
//            .description("Access Tocken")
//            .modelRef(new ModelRef("string"))
//            .parameterType("header")
//            .required(false)
//            .build();
//
//        List<Parameter> globalParamters = new ArrayList<>();
//        globalParamters.add(parameterBuilder);

        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(globalParamters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.dddproject"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("chat Spring Boot Rest API") //제일 큰 주제
                .version("1.0.0")
                .description("채팅 예제 swagger api입니다.")//내용
                .build();
    }

}
