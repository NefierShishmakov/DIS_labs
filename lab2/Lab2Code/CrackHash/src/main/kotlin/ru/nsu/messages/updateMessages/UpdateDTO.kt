package ru.nsu.messages.updateMessages

import kotlinx.serialization.Serializable
import org.bson.Document
import ru.nsu.data.Status

@Serializable
data class UpdateDTO (
    val requestId : String,
    val value : String,
    val status : Status
)

fun UpdateDTO.toDocument() : Document =
    Document("_id", this.requestId)
        .append("value", this.value)
        .append("status", this.status)