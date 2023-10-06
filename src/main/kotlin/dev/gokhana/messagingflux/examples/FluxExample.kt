package dev.gokhana.messagingflux.examples;

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

fun main() {
    val producer = Flux.range(1, 1000) // 1'den 1000'e kadar olan verileri üretir
            .delayElements(Duration.ofMillis(9)) // 1 milisaniye aralıklarla veri üretir
            .onBackpressureBuffer() // Tampona en fazla 10 veri koy (Bu kapasiteyi değiştirebilirsiniz)
            .doOnNext { value -> println("Producing: $value") }

    val consumer = producer
            .publishOn(Schedulers.boundedElastic()) // Tüketiciyi asenkron çalıştırır
            .doOnNext { value ->
                Thread.sleep(10) // Yavaş bir şekilde veriyi işler (tüketim hızını düşük gösterir)
                println("Consuming: $value")
            }
            .subscribe(
                    { value -> println("Consumed: $value") },
                    { error -> println("Error: ${error.message}") },
                    { println("Completed") }
            )

    Thread.sleep(15000) // Main thread'i bekleme
    consumer.dispose() // Tüketiciyi sonlandır
}
