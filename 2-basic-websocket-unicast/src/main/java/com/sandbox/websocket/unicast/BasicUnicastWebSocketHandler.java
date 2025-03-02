package com.sandbox.websocket.unicast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class BasicUnicastWebSocketHandler extends TextWebSocketHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connection established for session '{}'", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        record AuthenticationMessage(UUID userId) {}
        final var userId = OBJECT_MAPPER.readValue(message.getPayload(), AuthenticationMessage.class).userId();
        this.injectUserIdAsAttribute(session, userId);
        WebSocketSessionUnicastHolder.addSession(userId, session);
        log.info("Authentication message '{}' received from user id '{}", message.getPayload(), userId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        final var userId = getUserId(session);
        log.error("Error occurred for user id '{}", userId, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        final var userId = getUserId(session);
        log.info("Connection closed for user id '{}'", userId);
        WebSocketSessionUnicastHolder.removeSession(userId, session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void injectUserIdAsAttribute(WebSocketSession session, UUID userId) {
        session.getAttributes().put("user-id", userId.toString());
    }

    private UUID getUserId(WebSocketSession session) {
        return Optional.ofNullable(session.getAttributes().get("user-id"))
                .map(Object::toString)
                .map(UUID::fromString)
                .orElseThrow(() -> new IllegalArgumentException("User ID not found in handshake headers"));
    }
}
