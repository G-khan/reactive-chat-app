package dev.gokhana.messagingflow.examples

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main(): Unit =
        coroutineScope {
            val flow = flow {
                println("Emitter:    Start Cooking Pancakes")
                repeat(5) {
                    delay(100)
                    println("Emitter:    Pancake ${it + 1} ready!")
                    emit(it)
                }
            }.buffer() // Buffer the emissions

            val sharedFlow = flow.shareIn(this,
                    SharingStarted.WhileSubscribed()).map { it + 1 } // Create a shared flow

            val transformedFlow = sharedFlow.filter { it % 2 == 0 }
                    .transform { value ->
                        emit("Special Pancake $value (Even)")
                    }

            launch {
                sharedFlow.collect {
                    println("Collector:  Start eating $it")
                    delay(300)
                    println("Collector:  Finished eating $it")
                }
            }


            launch {
                transformedFlow.collect {
                    println("Transform: $it")
                }
            }
        }
