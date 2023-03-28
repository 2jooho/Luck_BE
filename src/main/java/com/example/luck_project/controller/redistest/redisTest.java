package com.example.luck_project.controller.redistest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class redisTest {

	private final RedisService redisService;

	@GetMapping("/redis")
	public String redisHello() {
		redisService.redisString();
		return "redisHello";
	}
}