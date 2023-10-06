package dev.gokhana.messagingflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories


@SpringBootApplication
@EnableR2dbcRepositories
class MessagingFlowApplication

fun main(args: Array<String>) {
    runApplication<MessagingFlowApplication>(*args)
}