package me.plony.kazanexpress.services

import me.plony.kazanexpress.configurations.ProductServiceProperties
import me.plony.kazanexpress.json.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder

@Service
class ProductService {
    @Autowired
    private lateinit var webClient: WebClient

    @Autowired
    private lateinit var properties: ProductServiceProperties
    suspend fun getProduct(productId: Int): Product = webClient
        .get()
        .uri(
            UriComponentsBuilder.fromHttpUrl(properties.url)
                .path("/api/v1/products/$productId")
                .build()
                .toUri()
        )
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .awaitBody()

}