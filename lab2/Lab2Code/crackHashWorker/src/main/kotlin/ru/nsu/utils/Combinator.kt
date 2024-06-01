package ru.nsu.utils

class Combinator(private val alphabet : List<Char>, private val partNumber : Int, private val partCount: Int) {
    private val stateArray = ArrayList<Int>()
    private var currentCombinationNumber = 0
    init {
        var start = partCount * partNumber
        if(partNumber == 0) {
            stateArray.add(-1)
        } else {
            while (start > 0) {
                stateArray.add((start - 1 ) % alphabet.size)
                start = (start - 1) / alphabet.size
            }
        }
    }

    fun getNextCombination() : List<Char> {
        var i = 0
        while(true) {
            if(++currentCombinationNumber > partCount){
                return "empty".toList()
            }
            val curr = stateArray[i]
            if(curr == alphabet.size - 1) {
                stateArray[i] = 0
                if(stateArray.size == i + 1){
                    stateArray.add(0)
                    break
                }
                i++
                continue
            }
            stateArray[i] = curr + 1
            break
        }
        return stateArray.stream().map(alphabet::get).toList()
    }
}