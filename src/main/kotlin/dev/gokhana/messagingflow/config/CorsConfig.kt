package dev.gokhana.messagingflow.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsWebFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        config.allowedOrigins = listOf("http://localhost:3000")
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("*")
        config.exposedHeaders = listOf("*")
        source.registerCorsConfiguration("/**", config)

        return CorsWebFilter(source)
    }
}
