package com.sandbox.websocket.broadcast;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class BasicBroadcastWebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new BasicBroadcastWebSocketHandler(), "/websocket-init-broadcast")
                .setAllowedOrigins("https://hoppscotch.io", "http://localhost:8080");
    }
}
