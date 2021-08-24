package me.plony.kazanexpress.order

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import me.plony.kazanexpress.internal.DataGatherer
import me.plony.kazanexpress.json.CustomerOrders
import me.plony.kazanexpress.services.CustomerService
import me.plony.kazanexpress.services.OrderService
import me.plony.kazanexpress.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderDataGatherer : DataGatherer<Int, CustomerOrders>() {
    @Autowired
    private lateinit var customerService: CustomerService
    @Autowired
    private lateinit var orderService: OrderService
    @Autowired
    private lateinit var productService: ProductService

    override suspend fun gather(input: Int): CustomerOrders = coroutineScope {
        val customerDeferred = async { customerService.getCustomer(input) }
        val orders = orderService.getOrders(input)
            .map {
                async {
                    productService.getProduct(it.id).run {
                        CustomerOrders.Order(
                            id = it.id,
                            productId = it.productId,
                            productTitle = title
                        )
                    }
                }
            }
            .map { it.await() }
        CustomerOrders(
            customerName = customerDeferred.await().name,
            orders = orders
        )
    }

}