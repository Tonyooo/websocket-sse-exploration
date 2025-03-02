package com.sandbox.websocket.broadcast;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class BasicBroadcastWebSocketHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connection established");
        WebSocketSessionBroadcastHolder.addSession(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("Message received: {}", message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Error occurred", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("Connection closed");
        WebSocketSessionBroadcastHolder.removeSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
