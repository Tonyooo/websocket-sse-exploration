package com.sandbox.websocket.pubsub;

import com.sandbox.websocket.pubsub.redis.RedisMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InternalUnicastMessageProducerEndpoint {
    private final RedisMessagePublisher redisMessagePublisher;

    public record MessageWrapper(UUID userId, String message) {}

    @PostMapping("/internal-message")
    public void publishMessageToClients(@RequestBody final MessageWrapper messageWrapper) {
        final var userId = messageWrapper.userId();
        log.info("Received message '{}' to be sent to user id '{}'...", messageWrapper.message(), userId);
        redisMessagePublisher.storePubSubEvent(userId, messageWrapper.message());
//
//        final var session = WebSocketSessionUnicastHolder.getSession(userId);
//        if(session == null) {
//            log.warn("Session not found for user id '{}'", userId);
//            return;
//        }
//        sendMessageToSession(userId, messageWrapper.message(), session);
    }

//    @SneakyThrows
//    private static void sendMessageToSession(UUID userId, String message, WebSocketSession session) {
//        log.info("Sending message '{}' to user id '{}' and session '{}'...", message, userId, session.getId());
//        session.sendMessage(new TextMessage(message));
//    }
}
