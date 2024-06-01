package ru.nsu.features.status

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.nsu.data.MongoDBUtil

fun Application.configureStatusRouting(){
    val mongoDBUtil by inject<MongoDBUtil>()
    routing {
        get("/api/hash/status") {
            val requestId = call.parameters["requestId"]
            println(requestId)
            val update = mongoDBUtil.getById(requestId)
            if(update == null){
                call.respond(HttpStatusCode.BadRequest, "Task does not exist!")
            } else{
                call.respond(StatusResponce(update.getString("status"), update.getString("value")))
            }
        }
    }
}