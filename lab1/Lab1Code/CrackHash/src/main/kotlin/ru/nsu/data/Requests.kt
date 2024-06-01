package ru.nsu.data

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

object Requests {
    val requests = ConcurrentHashMap<String, Request>()
    val executorService = Executors.newScheduledThreadPool(10)
}

data class Request(
    val requestId: String, // менеджер должен отправить клиенту идентификатор запроса, по которому тот сможет обратиться за получением ответа
    var value: String, // взломанный хеш либо ничего
    var status: Status
)

enum class Status {
    IN_PROGRESS, READY, ERROR
}