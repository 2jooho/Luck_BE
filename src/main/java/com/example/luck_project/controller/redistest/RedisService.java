package com.example.luck_project.controller.redistest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

	private final RedisTemplate<String, Object> redisTemplate;

	public void redisString() {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		operations.set("redisHello", "redisHello");
		String redis = (String)operations.get("redisHello");
		log.info(redis);
	}
}