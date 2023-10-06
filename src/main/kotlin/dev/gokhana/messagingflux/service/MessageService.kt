package dev.gokhana.messagingflux.service

import com.fasterxml.jackson.databind.ObjectMapper
import dev.gokhana.messagingflux.model.Message
import dev.gokhana.messagingflux.repository.MessageRepository
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MessageService(private val reactiveRedisTemplate: ReactiveStringRedisTemplate,
                     private val messageRepository: MessageRepository,
                     private val objectMapper: ObjectMapper) {
    private val MESSAGE_QUEUE = "messages"

    fun saveMessage(message: Message): Mono<Long> {
        return messageRepository.save(message).map { savedMessage ->
            reactiveRedisTemplate.convertAndSend(MESSAGE_QUEUE, serializeMessage(savedMessage))
        }.flatMap { it }

    }

    fun streamMessages(): Flux<Message> {
        return reactiveRedisTemplate.listenToChannel(MESSAGE_QUEUE).mapNotNull {
            deserializeMessage(it.message)
        }
    }

    private fun serializeMessage(message: Message): String {
        return objectMapper.writeValueAsString(message)
    }

    private fun deserializeMessage(messageJson: String): Message? {
        try {
            return objectMapper.readValue(messageJson, Message::class.java)
        } catch (e: Exception) {
            // Hata durumunu ele alabilirsiniz
            e.printStackTrace()
        }
        return null
    }
}
