package ru.nsu.features.update

import kotlinx.serialization.Serializable

@Serializable
data class UpdateRequest(
    val token: String,
    val result: String
)