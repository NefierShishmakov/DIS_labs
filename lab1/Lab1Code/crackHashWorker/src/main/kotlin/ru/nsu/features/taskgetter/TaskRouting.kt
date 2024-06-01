package ru.nsu.features.taskgetter

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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.nsu.utils.crack

@OptIn(DelicateCoroutinesApi::class)
fun Application.configureTaskRouting() {
    routing {
        post("/internal/api/worker/hash/crack/task") {
            val receive = call.receive(TaskRequest::class)
            GlobalScope.launch {
                val result = crack(receive.hash, receive.maxLen)
                patchResult(receive.requestId, result)
            }
            call.respond("Ok!")
        }
    }
}

private suspend fun patchResult(token: String, result: String) {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    val response = client.patch("http://server:8080/internal/api/manager/hash/crack/request") {
        contentType(ContentType.Application.Json)
        setBody(TaskResponce(token, result))
    }
    if (response.status.isSuccess()) {
        println("PATCH success")
    } else {
        println("PATCH failure!")
    }

}


//                val result = withTimeoutOrNull(60000L) {
//                    crack(receive.hash, receive.maxLen)
//                }
//                if (result != null) {
//                    patchResult(receive.requestId, result)
//                } else {
//                    println("Cracking operation timed out.")
//                    patchResult(receive.requestId, "none")
//                }