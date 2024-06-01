package ru.nsu.features.crack

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.nsu.data.Request
import ru.nsu.data.Requests
import ru.nsu.data.Status
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun Application.configureCrackRouting() {
    routing {
        post("/api/hash/crack") {
            val receive = call.receive(CrackRemoteRequest::class) // инстанс класса CrackRemoteRequest
            println("Received hash: " + receive.hash)
            val token = UUID.randomUUID().toString() // рандомная генерация токена
            println("Generated token: $token for hash $receive.hash")
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }

            val response = client.post("http://worker:8008/internal/api/worker/hash/crack/task") {
                contentType(ContentType.Application.Json)
                setBody(WorkerRequest(token, 1, 1, receive.hash, receive.maxLength))
            }
            if (response.status.isSuccess()) {
                Requests.requests[token] =
                    Request(token, "none", Status.IN_PROGRESS) // происходит запись в requests token
                println(Requests.requests[token])
                Requests.executorService.schedule({
                    Requests.requests[token]?.let { it1 ->
                        synchronized(it1) {
                            if (it1.status != Status.READY) {
                                it1.status = Status.ERROR
                                it1.value = "Time limit exceeded"
                            }
                        }
                    }
                }, 1, TimeUnit.MINUTES)

                call.respond(CrackRemoteResponce(token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Error starting the job of worker!")
            }
        }
    }
}
