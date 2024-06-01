package ru.nsu.features.crack

import kotlinx.serialization.Serializable

@Serializable
data class CrackRemoteRequest(
    val hash: String,
    val maxLength: Int
) // То, что отправляет пользователь

@Serializable
data class CrackRemoteResponce(
    val requestId: String
) // То, что отправляет менеджер нам пользователю

@Serializable
data class WorkerRequest(
    val requestId: String,
    val partNumber: Int,
    val partCount: Int,
    val hash: String,
    val maxLen: Int,
) // То, что отправляет manager worker