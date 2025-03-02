package com.sandbox.websocket.pubsub.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisMessageConsumerConfig {

    @Bean
    RedisMessageConsumerHandler redisMessageConsumerHandler() {
        return new RedisMessageConsumerHandler();
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(final RedisConnectionFactory connectionFactory, final RedisMessageConsumerHandler redisMessageConsumerHandler) {
        final var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(redisMessageConsumerHandler, PatternTopic.of("notif:*"));
        return container;
    }
}
