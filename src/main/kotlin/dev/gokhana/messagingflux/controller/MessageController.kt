package dev.gokhana.messagingflux.controller

import dev.gokhana.messagingflux.service.MessageService
import dev.gokhana.messagingflux.model.Message
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = ["http://localhost:3000"])
class MessageController(private val messageService: MessageService) {

    @PostMapping
    fun sendMessage(@RequestBody message: Message): Mono<Long> {
        return messageService.saveMessage(message)
    }

    @GetMapping(value = ["/stream"],
            produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamMessages(): Flux<Message> {
        return messageService.streamMessages()
    }
}

