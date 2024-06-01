package ru.nsu.features.update

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.nsu.data.Requests
import ru.nsu.data.Status

fun Application.configureUpdateRouting() {
    routing {
        patch("/internal/api/manager/hash/crack/request") {
            val request = call.receive(UpdateRequest::class)
            if (Requests.requests.containsKey(request.token)) {
                Requests.requests[request.token]?.let { it1 ->
                    synchronized(it1) {
                        if (it1.status != Status.ERROR) {
                            it1.status = Status.READY
                            it1.value = request.result
                        }
                    }
                }
                call.respond("OK!")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Task not found!")
            }
        }
    }
}