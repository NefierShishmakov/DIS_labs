package ru.nsu.utils.rabbitmq

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponce(
    val requestId : String,
    val value : String,
    val status: String
)