package ru.nsu.features.crack

import kotlinx.serialization.Serializable

// То, что приходит от клиента (postman)
@Serializable
data class CrackRemoteRequest(
    val hash: String,
    val maxLength: Int
)

// То, что отправляется клиенту (postman)
@Serializable
data class CrackRemoteResponce(
    val requestId : String
)

// То, что отправляется воркеру (postman)
@Serializable
data class WorkerRequest(
    val requestId : String,
    val partNumber : Int,
    val partCount : Int,
    val hash : String,
    val maxLen : Int,
)