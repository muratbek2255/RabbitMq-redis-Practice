package com.example.redisrabbitmqpractice.config;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class RedisLock {

    RedisTemplate<String, Long> redisTemplate;

    private static final String KEY_TEMPLATE = "lock:%s";


    public boolean acquireLock(long expireMills, String taskKey) {

        String lockey = getLockKey(taskKey);

        Long expiredAt = redisTemplate.opsForValue().get(lockey);

        long currentTimeMills = System.currentTimeMillis();

        if(Objects.nonNull(expiredAt)) {
            if(expiredAt <= currentTimeMills) {
                redisTemplate.delete(lockey);
            }else {
                return false;
            }
        }

        Long expire =  currentTimeMills + expireMills;

        return Optional.ofNullable(redisTemplate.opsForValue().setIfAbsent(lockey, expire)).orElse(false);
    }

    public void release(String taskKey) {
        String locKey = getLockKey(taskKey);

        redisTemplate.delete(locKey);
    }

    public String getLockKey(String key) {
        return String.format(KEY_TEMPLATE, key);
    }
}
