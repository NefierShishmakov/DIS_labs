package ru.nsu.utils

import org.paukov.combinatorics3.Generator
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

fun crack(hash: String, maxLen: Int): String {
    val lettersAndDigits = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val collection: Collection<String> = lettersAndDigits.map { it.toString() }

    for (i in 1..maxLen) {
        println(i)
        // сгенерировать перестановки с повторениями
        var result =
            Generator.permutation(collection).withRepetitions(i).stream().filter { it.joinToString("").hash() == hash }
                .findFirst()
        if (result.isPresent) {
            return result.get().toString()
        }
    }

    return "none"
}

// extension функция для встроенного класса String
fun String.hash(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(this.toByteArray(UTF_8))
    return digest.joinToString("") { "%02x".format(it) }
}