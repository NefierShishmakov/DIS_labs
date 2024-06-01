package ru.nsu.utils.rabbitmq

import kotlinx.serialization.Serializable

@Serializable
data class WorkRequest(
    val requestId : String,
    val partNumber : Int,
    val partCount : Int,
    val hash : String,
    val maxLen : Int
)