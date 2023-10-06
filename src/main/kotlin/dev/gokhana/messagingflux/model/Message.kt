package dev.gokhana.messagingflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("message")
data class Message(@Id var id: Long? = null, val username: String, val content: String)
