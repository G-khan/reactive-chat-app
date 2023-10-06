package dev.gokhana

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Hooks


@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}


@Component
class CommandLineAppStartupRunner : CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg args: String) {

        Hooks.onOperatorDebug()

        val flux = Flux.range(1, 5)
                .checkpoint("Initial Range")
                .map { it * 2 }
                .checkpoint("After Mapping")
                .filter { it % 3 == 0 }
                .checkpoint("After Filtering")
                .doOnNext { throw RuntimeException("Exception occurred!") }
                .doOnError { throwable ->
                    println("Error occurred: ${throwable.message}")
                }
                .log()

        flux.subscribe()
    }

}