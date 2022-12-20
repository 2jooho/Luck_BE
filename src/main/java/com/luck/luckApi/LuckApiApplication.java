package com.luck.luckApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LuckApiApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "./app/config/newsum/real-application.yml";
	public static void main(String[] args) {
		new SpringApplicationBuilder(LuckApiApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
