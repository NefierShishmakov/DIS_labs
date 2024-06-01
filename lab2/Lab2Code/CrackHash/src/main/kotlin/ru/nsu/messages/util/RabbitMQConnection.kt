package ru.nsu.messages.util

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel

class RabbitMQConnection(private val config: RabbitMQConfig) {

    private var connection: Connection? = null
    private var channel: Channel? = null
    init{
        connect()
    }
    private fun connect() {
        // Взято с официальной документации RabbitMQ
        val factory = ConnectionFactory()
        factory.host = config.host
        factory.port = config.port
        factory.username = config.username
        factory.password = config.password
        connection = factory.newConnection()
        channel = connection?.createChannel()
        // durable = false, т.е. он не сможет пережить перезапуск RabbitMQ
        // Он не будет авто-удалён, когда будут удалены все очереди
        channel?.exchangeDeclare(config.sendExchangeName, "direct")
        channel?.exchangeDeclare(config.receiveExchangeName, "direct")
        // durable = false, т.е. она будет сохранять своё состояние и восстанавливаться после перезапуска RabbitMQ
        // exclusive = false, означает, что очередь разрешает подключение несколько consumers
        // autoDelete = false, означает, что очередь не может автоматически себя удалять.
        // arguments = null
        channel?.queueDeclare(config.sendQueueName, true, false, false, null)
        channel?.queueDeclare(config.receiveQueueName, true, false, false, null)
        channel?.queueBind(config.sendQueueName, config.sendExchangeName, config.sendRoutingKey)
        channel?.queueBind(config.receiveQueueName, config.receiveExchangeName, config.receiveRoutingKey)
    }

    fun getSendChannel(): Channel? {
        return channel
    }
}