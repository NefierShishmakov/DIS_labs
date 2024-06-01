package ru.nsu.messages.util

data class RabbitMQConfig(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val sendQueueName: String,
    val sendExchangeName : String,
    val sendRoutingKey : String,
    val receiveQueueName : String,
    val receiveExchangeName : String,
    val receiveRoutingKey : String
)
