package dev.gokhana.messagingflow.service

import com.fasterxml.jackson.databind.ObjectMapper
import dev.gokhana.messagingflow.model.Message
import dev.gokhana.messagingflow.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.listenToChannelAsFlow
import org.springframework.stereotype.Service

@Service
class MessageService(
        private val reactiveRedisTemplate: ReactiveStringRedisTemplate,
        private val messageRepository: MessageRepository,
        private val objectMapper: ObjectMapper
) {
    private val messagesTopic = "messages"
    private val logger = org.slf4j.LoggerFactory.getLogger(MessageService::class.java)

    suspend fun saveMessage(message: Message): Long? {
        val savedMessage = messageRepository.save(message)
        logger.info("MessageService.saveMessage() called with message: {}", savedMessage)
        return reactiveRedisTemplate.convertAndSend(messagesTopic, serializeMessage(savedMessage)).awaitSingle()
    }

    fun streamMessages(): Flow<Message> {
        return reactiveRedisTemplate.listenToChannelAsFlow(messagesTopic).mapNotNull {
            deserializeMessage(it.message)
        }
    }

    fun getAllMessages(): Flow<Message> {
        return messageRepository.findAll().mapNotNull {
            it
        }
    }

    private fun serializeMessage(message: Message): String {
        return objectMapper.writeValueAsString(message)
    }

    private fun deserializeMessage(messageJson: String): Message? {
        try {
            return objectMapper.readValue(messageJson, Message::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
