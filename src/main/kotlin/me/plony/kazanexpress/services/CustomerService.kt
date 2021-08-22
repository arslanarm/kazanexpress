package me.plony.kazanexpress.services

import me.plony.kazanexpress.configurations.CustomerServiceProperties
import me.plony.kazanexpress.json.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder

@Service
class CustomerService {
    @Autowired
    private lateinit var webClient: WebClient

    @Autowired
    private lateinit var properties: CustomerServiceProperties

    suspend fun getCustomer(customerId: Int): Customer = webClient.get()
        .uri(
            UriComponentsBuilder.fromHttpUrl(properties.url)
                .path("/api/v1/users/$customerId")
                .build()
                .toUri()
        )
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .awaitBody()
}