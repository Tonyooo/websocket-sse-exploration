package com.sandbox.sse;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// Horrible static holder, do not do that at home
@UtilityClass
public class SseEmitterUnicastHolder {
    private static final Map<UUID, SseEmitter> SSE_EMITTERS = new ConcurrentHashMap<>();

    public void add(UUID userId, SseEmitter sseEmitter) {
        SSE_EMITTERS.put(userId, sseEmitter);
    }

    public void remove(UUID userId, SseEmitter sseEmitter) {
        SSE_EMITTERS.remove(userId, sseEmitter);
    }

    public SseEmitter get(UUID userId) {
        return SSE_EMITTERS.get(userId);
    }
}
