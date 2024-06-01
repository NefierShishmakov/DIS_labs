package ru.nsu.features.taskgetter

import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(
    val requestId: String,
    val partNumber: Int,
    val partCount: Int,
    val hash: String,
    val maxLen: Int,
)

@Serializable
data class TaskResponce(
    val token: String,
    val result: String
)