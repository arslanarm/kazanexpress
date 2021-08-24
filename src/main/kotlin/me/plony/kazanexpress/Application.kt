package me.plony.kazanexpress

import me.plony.kazanexpress.configurations.CustomerServiceProperties
import me.plony.kazanexpress.configurations.OrderServiceProperties
import me.plony.kazanexpress.configurations.ProductServiceProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@SpringBootApplication
@EnableConfigurationProperties(
    CustomerServiceProperties::class,
    OrderServiceProperties::class,
    ProductServiceProperties::class
)
class Application {
    @Bean
    fun webClient() = WebClient.builder()
        .clientConnector(
            ReactorClientHttpConnector(
                HttpClient.create(
                    ConnectionProvider.builder("default")
                        .pendingAcquireTimeout(Duration.ofSeconds(1))
                        .build()
                )
            )
        )
        .build()
}

fun main(args: Array<String>) {
    runApplication<Application>(*args) // TODO Kafka logging
}


