package me.plony.kazanexpress.services

import me.plony.kazanexpress.configurations.OrderServiceProperties
import me.plony.kazanexpress.json.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder

@Service
class OrderService {
    @Autowired
    private lateinit var webClient: WebClient

    @Autowired
    private lateinit var properties: OrderServiceProperties
    suspend fun getOrders(clientId: Int): List<Order> = webClient
        .get()
        .uri(
            UriComponentsBuilder.fromHttpUrl(properties.url)
                .path("/api/v1/orders/")
                .queryParam("client_id", clientId)
                .build()
                .toUri()
        )
        .retrieve()
        .awaitBody()
}