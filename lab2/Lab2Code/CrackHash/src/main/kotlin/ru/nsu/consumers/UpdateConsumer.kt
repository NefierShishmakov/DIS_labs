package ru.nsu.consumers

import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import ru.nsu.messages.updateMessages.RabbitMQWorker

fun Application.configureUpdateConsumer(){
    val workerModule by inject<RabbitMQWorker>()
    workerModule.startConsuming("update_queue")
}