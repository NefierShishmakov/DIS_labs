package ru.nsu.util

import kotlin.math.pow

fun separate(maxLen : Int, workerCount : Int) : ArrayList<Int>{
    // Этот массив, содержит число перестановок, которые будут отправляться воркерам
    val array = ArrayList<Int>()
    var operationCount = 0.0
    val alphabetPower = 62.0 // английский алфавит в двух регистрах (26 + 26) + цифры (10)
    for (i in 1..maxLen) {
        operationCount += alphabetPower.pow(i.toDouble())
    }
    for(i in 1..workerCount){
        if(i == workerCount - 1){
            array.add(operationCount.toInt() / workerCount + operationCount.toInt() % workerCount)
        } else {
            array.add(operationCount.toInt() / workerCount)
        }
    }
    return array
}