package com.example.luck_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {
		"classpath:/profiles/database-local.yml"
})
//${spring.profiles.active}
public class LuckProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuckProjectApplication.class, args);
	}

}
