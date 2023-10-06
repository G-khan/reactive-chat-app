package dev.gokhana.messagingflow.examples

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun fetchData(id: Int): String {
    delay(if (id % 2 == 0) 2000L else 1000L)
    return "Data for ID $id"
}

fun main() = runBlocking {
    val userIds = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val elapsedTime = measureTimeMillis {
        coroutineScope {
            userIds.map { id ->
                async {
                    println("User ${id}: ${fetchData(id)}")
                }
            }.awaitAll()
        }
    }

    println("Data fetching completed in $elapsedTime ms")
}
