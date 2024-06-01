package ru.nsu

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.nsu.consumers.configureMessageConsumer
import ru.nsu.di.rabbitMQModule
import ru.nsu.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8008, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    installKoin()
    configureSerialization()
    configureMessageConsumer()
}

fun Application.installKoin() {
    install(Koin){
        slf4jLogger()
        modules(rabbitMQModule)
    }
}
