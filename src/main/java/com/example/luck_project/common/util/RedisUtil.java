package com.example.luck_project.common.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisUtil {
    private final RedisTemplate<String, Object> redisCacheTemplate;

    public Object getValue(String key) {
        return redisCacheTemplate.opsForValue().get(key);
    }
    public Boolean deleteData(String key) {
        return redisCacheTemplate.delete(key);
    }

    public void setValue(String key, String value) {redisCacheTemplate.opsForValue().set(key, value);}

    public Long incrementAndGet(String key) {
        return redisCacheTemplate.opsForValue().increment(key);
    }
    public Map<Object,Object> getHashAll(String key) {
        return redisCacheTemplate.opsForHash().entries(key);
    }

    //키, 넣을 값, 유지시간
    public Boolean putHashData (String key, Map<Object, Object> valObject, long expireTime) {
        redisCacheTemplate.opsForHash().putAll(key,valObject);
        return redisCacheTemplate.expire(key, Duration.ofSeconds(expireTime));
    }

    //키, 필드키
    public Object getHashDataByFieldKey(String key, String fieldKey) {
        return redisCacheTemplate.opsForHash().get(key, fieldKey);
    }

}
