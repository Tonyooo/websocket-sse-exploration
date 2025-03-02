package com.sandbox.websocket.broadcast;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@RestController
public class InternalBroadcastMessageProducerEndpoint {

    public record MessageWrapper(String message) {}

    @PostMapping("/internal-message")
    public void publishMessageToClients(@RequestBody final MessageWrapper messageWrapper) {
        log.info("Received message to be broadcasted: '{}'", messageWrapper.message());
        WebSocketSessionBroadcastHolder.getSessions().forEach(session -> sendMessageToSession(messageWrapper.message(), session));
    }

    @SneakyThrows
    private static void sendMessageToSession(String message, WebSocketSession session) {
        log.info("Sending message '{}' to session '{}'...", message, session.getId());
        session.sendMessage(new TextMessage(message));
    }
}
