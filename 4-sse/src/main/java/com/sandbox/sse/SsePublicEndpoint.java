package com.sandbox.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Controller
@Slf4j
public class SsePublicEndpoint {

    @GetMapping(path="/events", produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter handle(@RequestParam("userId") final UUID userId) throws IOException {
        log.info("Received SSE init request for user id '{}'", userId);
        final var emitter = new SseEmitter(Duration.ofHours(1).toMillis()); // Timeout of the connection
        emitter.onCompletion(() -> SseEmitterUnicastHolder.remove(userId, emitter)); // Covers errors and timeouts
        SseEmitterUnicastHolder.add(userId, emitter);
        emitter.send(
                SseEmitter.event()
                        .data("Welcome user %s!".formatted(userId))
                        .id("1")
                        .comment("event comment")
                        .reconnectTime(10_000)
                        .build()
        );
        return emitter;
    }
}
