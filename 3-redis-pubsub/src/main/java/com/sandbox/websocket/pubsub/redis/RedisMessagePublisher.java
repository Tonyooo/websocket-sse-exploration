package com.sandbox.websocket.pubsub.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisMessagePublisher {
    private static final String CHANNEL_PATTERN = "notif:%s";
    private final StringRedisTemplate stringRedisTemplate;

    public void storePubSubEvent(final UUID userId, String message) {
        final var channel = CHANNEL_PATTERN.formatted(userId.toString());
        log.info("Storing message '{}' for user id '{}' to channel '{}'...", message, userId, channel);
        stringRedisTemplate.convertAndSend(channel, message);
    }
}
