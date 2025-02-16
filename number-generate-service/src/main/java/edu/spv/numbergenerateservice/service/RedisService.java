package edu.spv.numbergenerateservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    /**
     * Save passed orderNumber to Redis if the passed value is absent
     *
     * @param orderNumber
     * @return true if value is saved successfully
     */
    public boolean saveIfAbsent(String orderNumber) {
        Boolean result = redisTemplate.opsForValue()
                .setIfAbsent(orderNumber, "1", 1, TimeUnit.DAYS);
        return result != null && result;
    }
}
