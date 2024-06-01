package ru.nsu.features.status

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponce(
    val status: String,
    val data: String
)