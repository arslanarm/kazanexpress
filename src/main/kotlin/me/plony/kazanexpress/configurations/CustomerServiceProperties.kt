package me.plony.kazanexpress.configurations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "customer")
data class CustomerServiceProperties(
    var url: String
)