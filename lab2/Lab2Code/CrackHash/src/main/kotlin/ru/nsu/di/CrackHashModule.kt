package ru.nsu.di

import org.koin.dsl.module
import ru.nsu.data.MongoDBUtil
import ru.nsu.messages.crackMessages.RabbitMQSender
import ru.nsu.messages.util.RabbitMQConfig
import ru.nsu.messages.util.RabbitMQConnection
import ru.nsu.messages.updateMessages.RabbitMQWorker

// Dependency injection на koin
val UtilModule = module(createdAtStart = true) {
    single {
        RabbitMQConfig(
            host = "rabbitmq",
            port = 5672,
            username = "guest",
            password = "guest",
            sendQueueName = "worker_queue", // Сообщение воркеру посылается в эту очередь
            // Про direct exchange - если применяется обменник по умолчанию, то сообщение будет маршрутизироваться
            // в очередь с именем равным ключу маршрутизации сообщения
            sendExchangeName = "worker_exchange",
            sendRoutingKey = "worker_routing", // Ключ маршрутизации
            receiveQueueName = "update_queue",
            receiveExchangeName = "update_exchange",
            receiveRoutingKey = "update_routing"
        )
    }
    single{
        MongoDBUtil()
    }
    single { RabbitMQConnection(get<RabbitMQConfig>()) }
    single { RabbitMQWorker(get<RabbitMQConnection>().getSendChannel(), get<MongoDBUtil>()) }
    single { RabbitMQSender(get<RabbitMQConnection>().getSendChannel()) }
}