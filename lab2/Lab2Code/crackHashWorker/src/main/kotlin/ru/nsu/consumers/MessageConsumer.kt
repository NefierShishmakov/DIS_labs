package ru.nsu.consumers

import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import ru.nsu.utils.rabbitmq.RabbitMQWorker

fun Application.configureMessageConsumer() {
    val workerModule by inject<RabbitMQWorker>()
    workerModule.startConsuming("worker_queue")
}