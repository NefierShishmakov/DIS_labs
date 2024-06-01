package ru.nsu.features.crack

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document
import org.koin.ktor.ext.inject
import ru.nsu.data.MongoDBUtil
import ru.nsu.data.Status
import ru.nsu.messages.crackMessages.RabbitMQSender
import ru.nsu.messages.util.RabbitMQConfig
import ru.nsu.util.separate
import java.util.*

fun Application.configureCrackRouting(){
    // dependency injection
    val mongoDBUtil by inject<MongoDBUtil>()
    val sender by inject<RabbitMQSender>()
    val config by inject<RabbitMQConfig>()

    routing {
        post("/api/hash/crack"){
            val receive = call.receive(CrackRemoteRequest::class)
            val token = UUID.randomUUID().toString()
            // Здесь мы получаем массив чисел - количество перестановок для перебора каждому воркеру
            val countArr = separate(receive.maxLength, System.getenv("WORKER_COUNT").toInt())
            for(i in 0..<countArr.size){
                val workerRequest = WorkerRequest(token, i, countArr[i], receive.hash, receive.maxLength)
                val message = Json.encodeToString(workerRequest)
                sender.sendMessage(config.sendRoutingKey, config.sendExchangeName, message)
            }
            val status = Document("_id", token).append("value", "none").append("status", Status.IN_PROGRESS.toString())
            mongoDBUtil.insertUpdate(status)
            println("SENT!")
            call.respond(CrackRemoteResponce(token))
        }
    }
}
