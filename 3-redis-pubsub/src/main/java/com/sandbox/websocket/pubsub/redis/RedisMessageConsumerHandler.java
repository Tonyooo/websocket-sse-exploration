package com.sandbox.websocket.pubsub.redis;

import com.sandbox.websocket.pubsub.WebSocketSessionUnicastHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.socket.TextMessage;

import java.util.UUID;

@Slf4j
public class RedisMessageConsumerHandler implements MessageListener {
    @Override
    @SneakyThrows
    public void onMessage(Message message, byte[] pattern) {
        final var channel = new String(message.getChannel());
        final var messageBody = new String(message.getBody());
        log.info("Message received from Redis channel '{}': '{}'; fowarding to suitable websocket session...", channel, messageBody);
        final var userId = extractUserIdFromChannel(channel);
        final var maybeWebSocketSession = WebSocketSessionUnicastHolder.getSession(userId);
        if (maybeWebSocketSession == null) {
            log.warn("No websocket session found for user id '{}'", userId);
        } else {
            log.info("Sending message '{}' to user id '{}' and session '{}'...", messageBody, userId, maybeWebSocketSession.getId());
            maybeWebSocketSession.sendMessage(new TextMessage(messageBody));
        }
    }

    private UUID extractUserIdFromChannel(final String channel) {
        return UUID.fromString(channel.split(":")[1]);
    }
}
