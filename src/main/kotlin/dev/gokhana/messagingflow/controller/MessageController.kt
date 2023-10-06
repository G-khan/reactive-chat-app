package dev.gokhana.messagingflow.controller
import dev.gokhana.messagingflow.model.Message
import dev.gokhana.messagingflow.service.MessageService
import kotlinx.coroutines.flow.Flow
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messages")
class MessageController(private val messageService: MessageService) {

    val logger = LoggerFactory.getLogger(MessageController::class.java)

    @PostMapping
    suspend fun sendMessage(@RequestBody message: Message): Long? {
        logger.info("MessageController.sendMessage() called with message: {}", message)
        return messageService.saveMessage(message)
    }

    @GetMapping(value = ["/stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamMessages(): Flow<Message?> {
        return messageService.streamMessages()
    }

    @GetMapping
    fun getAllMessages(): Flow<Message?> {
        return messageService.getAllMessages()
    }
}
