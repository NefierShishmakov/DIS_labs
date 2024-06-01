package ru.nsu.utils

import org.paukov.combinatorics3.Generator
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

// Old and deprecated
suspend fun crack(hash : String, maxLen : Int) : String{
    val lettersAndDigits = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val collection: Collection<String> = lettersAndDigits.map { it.toString() }

    for(i in 1..maxLen){
        println(i)
        var result = Generator.permutation(collection).withRepetitions(i).stream().filter{it.joinToString("").hash() == hash}.findFirst()
        if (result.isPresent) {
            return result.get().toString()
        }
    }

    return "none"
}

suspend fun crackPart(hash: String, partNumber : Int, partCount: Int) : String {
    val lettersAndDigits = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val combinator = Combinator(lettersAndDigits, partNumber, partCount)
    var i = 0
    while(i++ < partCount){
        val combination = combinator.getNextCombination().joinToString("")
        if(combination.hash() == hash){
            return combination
        }
    }
    return ""
}

fun String.hash(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(this.toByteArray(UTF_8))
    return digest.joinToString("") { "%02x".format(it) }
}