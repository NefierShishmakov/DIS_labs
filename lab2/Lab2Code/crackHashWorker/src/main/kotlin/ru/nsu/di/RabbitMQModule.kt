package ru.nsu.di

import org.koin.dsl.module
import ru.nsu.utils.rabbitmq.RabbitMQConfig
import ru.nsu.utils.rabbitmq.RabbitMQConnection
import ru.nsu.utils.rabbitmq.RabbitMQWorker

val rabbitMQModule = module {
    single {
        RabbitMQConfig(
            host = "rabbitmq",
            port = 5672,
            username = "guest",
            password = "guest",
            sendQueueName = "update_queue",
            sendExchangeName = "update_exchange",
            sendRoutingKey = "update_routing",
            receiveQueueName = "worker_queue",
            receiveExchangeName = "worker_exchange",
            receiveRoutingKey = "worker_routing"
        )
    }
    single { RabbitMQConnection(get<RabbitMQConfig>()) }
    single { RabbitMQWorker(get<RabbitMQConnection>().getChannel()!!) }
}