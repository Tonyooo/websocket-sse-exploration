package com.sandbox.websocket.broadcast;

import lombok.experimental.UtilityClass;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

// Horrible static holder, do not do that at home
@UtilityClass
public class WebSocketSessionBroadcastHolder {
    private static final Set<WebSocketSession> SESSIONS = new HashSet<>();

    public void addSession(WebSocketSession session) {
        SESSIONS.add(session);
    }

    public void removeSession(WebSocketSession session) {
        SESSIONS.remove(session);
    }

    public Set<WebSocketSession> getSessions() {
        return Set.copyOf(SESSIONS);
    }
}
