import java.io.File

val last25Numbers: MutableList<Long> = mutableListOf();

fun checkNumber(number: Long): Boolean {
    last25Numbers.indices.forEach { i ->
        last25Numbers.indices.dropWhile { it == i }.forEach { j ->
            if (last25Numbers[i] + last25Numbers[j] == number) {
                last25Numbers.removeAt(0);
                last25Numbers.add(number);
                return true;
            }
        }
    }
    return false;
}

fun findContiguousSum(number: Long, list: List<Long>): Long {
    list.indices.forEach { i ->
        var sum: Long = list[i];
        for (j in list.indices.drop(i + 1)) {
            sum += list[j];
            if (sum == number) {
                val sublist: List<Long> = list.subList(i, j + 1);
                return sublist.min()!! + sublist.max()!!;
            }
            if (sum > number) {
                break;
            }
        }
    }
    return 0;
}

fun main() {
    val inputList: List<Long> = File("input/day09.txt").readLines().map { it.toLong() };

    inputList.forEach {
        if (last25Numbers.size != 25) {
            last25Numbers.add(it);
        }
        else if (!checkNumber(it)) {
            println(it);
            println(findContiguousSum(it, inputList));
            return;
        }
    }
}