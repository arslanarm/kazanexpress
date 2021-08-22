package me.plony.kazanexpress.configurations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("product")
data class ProductServiceProperties(
    var url: String
)