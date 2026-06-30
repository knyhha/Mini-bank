package com.example.bank.service;

import com.example.bank.model.dto.SessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String PREFIX = "session:";
    private static final Duration TTL = Duration.ofMinutes(30);

    public void save(String tokenHash, SessionData data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(PREFIX + tokenHash, json, TTL);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

     public Optional<SessionData> findByTokenHash(String tokenHash) {
        String json = redisTemplate.opsForValue().get(PREFIX + tokenHash);
        if (json == null) Optional.empty();

        try {
            return Optional.of(objectMapper.readValue(json, SessionData.class));
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
     }

    public void delete(String tokenHash) {
        redisTemplate.delete(PREFIX + tokenHash);
    }
}
