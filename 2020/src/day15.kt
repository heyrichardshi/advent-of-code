import java.io.File

val spokenNumbers: MutableList<Int> = mutableListOf()
val lastIndex: HashMap<Int, Int> = hashMapOf()

fun getLastIndex(number: Int): Int {
    val index = lastIndex.getOrDefault(number, -1)
    lastIndex[number] = spokenNumbers.size - 1
    return index
}

fun nextNumber(): Int {
    val lastElement = spokenNumbers.last()
    val lastIndex = getLastIndex(lastElement)
    return if (lastIndex == -1) {0} else {spokenNumbers.size - lastIndex - 1}
}

fun main() {
    val inputList: List<Int> = File("input/day15.txt").readLines().flatMap {
        it.split(",")
    }.map {
        it.toInt()
    }
    inputList.forEach {
        spokenNumbers.add(it);
        lastIndex[it] = spokenNumbers.size - 1
    }
    
    while (spokenNumbers.size != 2020) {
        spokenNumbers.add(nextNumber())
    }
    println(spokenNumbers.last())
    while (spokenNumbers.size != 30000000) {
        spokenNumbers.add(nextNumber())
    }
    println(spokenNumbers.last())
}