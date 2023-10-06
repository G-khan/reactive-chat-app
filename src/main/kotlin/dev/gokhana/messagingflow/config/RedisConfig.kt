package dev.gokhana.messagingflow.config

import com.fasterxml.jackson.databind.ObjectMapper
import dev.gokhana.messagingflow.model.Message
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun reactiveRedisTemplate(
            @Qualifier("reactiveRedisConnectionFactory") connectionFactory: ReactiveRedisConnectionFactory,
            objectMapper: ObjectMapper
    ): ReactiveRedisTemplate<String, Message> {
        val messageSerializer = Jackson2JsonRedisSerializer(Message::class.java)
        val messageSerializationContext =
                RedisSerializationContext.newSerializationContext<String, Message>(messageSerializer)
                        .key(StringRedisSerializer.UTF_8)
                        .hashKey(StringRedisSerializer.UTF_8)
                        .hashValue(messageSerializer)
                        .build()

        return ReactiveRedisTemplate(connectionFactory, messageSerializationContext)
    }

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory("localhost", 6379)
    }


}
