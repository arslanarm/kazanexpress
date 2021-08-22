package me.plony.kazanexpress

import kotlinx.coroutines.runBlocking
import me.plony.kazanexpress.configurations.CustomerServiceProperties
import me.plony.kazanexpress.order.OrderGatewayController
import me.plony.kazanexpress.services.CustomerService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [Application::class]
)
@AutoConfigureWebTestClient
@ComponentScan(basePackageClasses = [CustomerService::class, CustomerServiceProperties::class])
class ApplicationTest {
    @Mock
    private lateinit var customerService: CustomerService

    @Mock
    private lateinit var orderGatewayController: OrderGatewayController

//    @Autowired
//    private lateinit var testClient: WebTestClient

    @Test
    fun `customer service test`() = runBlocking {
        println(orderGatewayController.orders(1))
    }
}