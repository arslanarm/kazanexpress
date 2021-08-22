package me.plony.kazanexpress.configurations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "order")
data class OrderServiceProperties(
    var url: String
)