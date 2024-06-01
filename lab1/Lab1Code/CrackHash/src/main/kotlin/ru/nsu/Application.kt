package ru.nsu

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.nsu.features.crack.configureCrackRouting
import ru.nsu.features.status.configureStatusRouting
import ru.nsu.features.update.configureUpdateRouting
import ru.nsu.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureCrackRouting()
    configureStatusRouting()
    configureUpdateRouting()
}
