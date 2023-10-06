package dev.gokhana.messagingflux.repository

import dev.gokhana.messagingflux.model.Message
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository  : R2dbcRepository<Message, String> {

}