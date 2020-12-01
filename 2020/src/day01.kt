import java.io.File

fun main() {
    val numbers: List<Int> = File("input/day01.txt").readLines().map {it.toInt()}

    for (n in numbers) {
        if (numbers.map {n + it}.contains(2020)) {
            println(n * (2020 - n));
            break;
        }
    }

    for (i in numbers.indices) {
        for (j in i until numbers.size) {
            for (k in j until numbers.size) {
                if (numbers[i] + numbers[j] + numbers[k] == 2020) {
                    println(numbers[i] * numbers[j] * numbers[k]);
                    return;
                }
            }
        }
    }
}