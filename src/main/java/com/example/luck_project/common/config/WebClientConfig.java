//package com.example.luck_project.common.config;
//
//import io.netty.channel.ChannelOption;
//import io.netty.handler.timeout.ReadTimeoutHandler;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpHeaders;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import org.springframework.web.reactive.function.client.ExchangeStrategies;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.netty.http.client.HttpClient;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class WebClientConfig {
//
//    public static final String CALL_KEY = "X-SOURCE-DOMAIN";
//    public static final String CALL_VAL = "ORD";
//
////    @Value("${endpoint.host}")
//    private String endpoint = "http://localhost:8080/luck";
//
////    @Value("${endpoint.connect-timeout}")
//    private int connetTimeout= 3000;
//
////    @Value("${endpoint.read-timeout}")
//    private long readTimeout = 3000;
//
//    @Bean
//    public WebClient webClient() {
//        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
//                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1 * 1024 * 1024))// 1mb
//                .build();
//
//        // Timeout 설정
//        HttpClient client = HttpClient.create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connetTimeout)
//                .doOnConnected(conn -> conn
//                        .addHandlerFirst(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)));
//
//        return WebClient.builder()
//                .baseUrl(endpoint)
//                .clientConnector(new ReactorClientHttpConnector(client))
//                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .defaultHeader("test")
//                .exchangeStrategies(exchangeStrategies)
//                .build();
//    }
//}