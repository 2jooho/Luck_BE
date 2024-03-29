package com.example.luck_project;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableBatchProcessing
@EnableScheduling
//@PropertySource(value = {
//		"classpath:/profiles/database-local.yml"
//})
//${spring.profiles.active}
public class LuckProjectApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LuckProjectApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(LuckProjectApplication.class, args);
	}

}
