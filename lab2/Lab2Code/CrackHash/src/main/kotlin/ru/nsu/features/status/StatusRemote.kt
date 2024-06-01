package ru.nsu.features.status

import kotlinx.serialization.Serializable

// Отправляется на get запрос
@Serializable
data class StatusResponce(
    val status: String,
    val data : String
)