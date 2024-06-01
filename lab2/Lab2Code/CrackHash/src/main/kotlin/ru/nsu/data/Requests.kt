package ru.nsu.data

import java.util.concurrent.ConcurrentHashMap

object Requests {
    val counters = ConcurrentHashMap<String, Int>()
}

enum class Status {
    IN_PROGRESS, READY, ERROR
}