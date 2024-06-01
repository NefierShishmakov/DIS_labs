package ru.nsu.features.status

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.nsu.data.Requests
import ru.nsu.data.Status

fun Application.configureStatusRouting() {
    routing {
        get("/api/hash/status") {
            val requestId = call.parameters["requestId"]
            println(requestId)
            val request = Requests.requests[requestId]
            println(request)
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Task does not exist!")
            } else {
                val status: Status
                val value: String
                synchronized(request) {
                    status = request.status
                    value = request.value
                }
                call.respond(StatusResponce(status.name, value))
            }
        }
    }
}