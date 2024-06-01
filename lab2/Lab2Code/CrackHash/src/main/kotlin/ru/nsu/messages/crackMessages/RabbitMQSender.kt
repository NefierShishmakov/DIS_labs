package ru.nsu.messages.crackMessages

import com.rabbitmq.client.Channel

class RabbitMQSender(private val channel: Channel?) {
    fun sendMessage(routingKey: String, exchangeName : String, message: String){
        channel?.basicPublish(exchangeName, routingKey, null, message.toByteArray(Charsets.UTF_8))
    }
}