package com.sandbox.sse;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;
import java.util.random.RandomGenerator;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InternalUnicastMessageProducerEndpoint {

    public record MessageWrapper(UUID userId, String message) {}

    @PostMapping("/internal-message")
    public void publishMessageToClients(@RequestBody final MessageWrapper messageWrapper) {
        final var userId = messageWrapper.userId();
        log.info("Received message '{}' to be sent to user id '{}'...", messageWrapper.message(), userId);
        
        final var sseEmitter = SseEmitterUnicastHolder.get(userId);
        if(sseEmitter == null) {
            log.warn("Emitter not found for user id '{}'", userId);
            return;
        }
        sendMessageToSession(userId, messageWrapper.message(), sseEmitter);
    }

    @SneakyThrows
    private static void sendMessageToSession(UUID userId, String message, SseEmitter sseEmitter) {
        log.info("Sending message '{}' to user id '{}'...", message, userId);
        sseEmitter.send(
                SseEmitter.event()
                        .data(message)
                        .id(String.valueOf(RandomGenerator.getDefault().nextInt(100_000)))
                        .comment("event comment")
                        .reconnectTime(10_000)
                        .build()
        );
    }
}
