package dev.gokhana.messagingflow.repository

import dev.gokhana.messagingflow.model.Message
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository  : CoroutineCrudRepository<Message, String> {

    override suspend fun <S : Message> save(entity: S): Message
    override fun findAll(): Flow<Message>

}