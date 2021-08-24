package me.plony.kazanexpress.order

import me.plony.kazanexpress.json.CustomerOrders
import me.plony.kazanexpress.services.CustomerService
import me.plony.kazanexpress.services.OrderService
import me.plony.kazanexpress.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderGatewayController {
    @Autowired
    lateinit var gatherer: OrderDataGatherer

    @GetMapping("/api/v1/orders/")
    suspend fun orders(@RequestParam(name = "client_id") clientId: Int): CustomerOrders =
        gatherer.gather(clientId)
}