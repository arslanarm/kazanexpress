package me.plony.kazanexpress

import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import me.plony.kazanexpress.configurations.CustomerServiceProperties
import me.plony.kazanexpress.configurations.OrderServiceProperties
import me.plony.kazanexpress.configurations.ProductServiceProperties
import me.plony.kazanexpress.json.Customer
import me.plony.kazanexpress.json.CustomerOrders
import me.plony.kazanexpress.json.Order
import me.plony.kazanexpress.json.Product
import me.plony.kazanexpress.order.OrderGatewayController
import me.plony.kazanexpress.services.CustomerService
import me.plony.kazanexpress.services.OrderService
import me.plony.kazanexpress.services.ProductService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.mockito.BDDMockito.`when`
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy
import org.testcontainers.images.builder.ImageFromDockerfile
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.io.path.Path


@Testcontainers
@RunWith(SpringRunner::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [Application::class]
)
class ApplicationTest {

    @Container
    val container: ServiceTestContainer = ServiceTestContainer(
        ImageFromDockerfile().withDockerfile(Path("src/test/python/Dockerfile")))
        .withFileSystemBind("src/test/python/", "/service/")
        .withExposedPorts(5000)
        .withCommand("python /service/test.py")
        .withLogConsumer { Logger.getLogger("Test").log(Level.INFO, it.utf8String.trim()) }


    @Autowired
    private lateinit var customerServiceProperties: CustomerServiceProperties

    @Autowired
    private lateinit var orderServiceProperties: OrderServiceProperties

    @Autowired
    private lateinit var productServiceProperties: ProductServiceProperties

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    private lateinit var orderService: OrderService

    @Autowired
    private lateinit var productService: ProductService

    @Before
    fun configure() {
        container.start()
        val containerHost = container.host
        val containerPort = container.getMappedPort(5000)
        customerServiceProperties.url = "http://$containerHost:${containerPort}"
        orderServiceProperties.url = "http://$containerHost:${containerPort}"
        productServiceProperties.url = "http://$containerHost:${containerPort}"
    }

    @Autowired
    private lateinit var orderGatewayController: OrderGatewayController

    @Test
    fun `test services`() = runBlocking {
        assertTrue(customerService.getCustomer(1) == Customer(
            1,
            "Test"
        ))

        assertTrue(orderService.getOrders(1) == listOf(Order(
            1,
            1
        )))

        assertTrue(productService.getProduct(1) == Product(
            1,
            "test"
        ))
    }

    @Test
    fun `test controller`(): Unit = runBlocking {
        assert(orderGatewayController.orders(1) == CustomerOrders(
            "Test",
            listOf(
                CustomerOrders.Order(
                    1,
                    1,
                    "test"
                )
            )
        ))
    }


}