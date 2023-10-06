package dev.gokhana.messagingflow.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("message")
data class Message(@Id @JsonIgnore var id: Long? = null, val username: String, val content: String)
