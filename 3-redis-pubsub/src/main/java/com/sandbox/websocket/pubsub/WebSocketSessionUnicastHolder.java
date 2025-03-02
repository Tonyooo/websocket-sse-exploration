package com.sandbox.websocket.pubsub;

import lombok.experimental.UtilityClass;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// Horrible static holder, do not do that at home
@UtilityClass
public class WebSocketSessionUnicastHolder {
    private static final Map<UUID, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    public void addSession(UUID userId, WebSocketSession session) {
        SESSIONS.put(userId, session);
    }

    public void removeSession(UUID userId, WebSocketSession session) {
        SESSIONS.remove(userId, session);
    }

    public WebSocketSession getSession(UUID userId) {
        return SESSIONS.get(userId);
    }
}
