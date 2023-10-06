package dev.gokhana.messagingflow.examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val flow = flow {
        for (i in 1..100) {
            delay(100)
            emit(i)
        }
    }

    flow.buffer(10)
            .collect { value ->
                println(value)
                delay(300)
            }
}