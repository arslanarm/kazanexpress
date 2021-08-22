package me.plony.kazanexpress.json

data class CustomerOrders(
    val customerName: String,
    val orders: List<Order>
) {
    data class Order(
        val id: Int,
        val productId: Int,
        val productTitle: String
    )
}
