import java.io.File

var lookup: HashMap<Int, Long> = hashMapOf();

fun permutations(number: Int, list: List<Int>): Long {
    if (list.isEmpty()) {
        return 0;
    }
    if (list.size == 1) {
        return 1;
    }
    var permutations: Long = 0;
    for (i in 0..2) {
        if (list.size < i + 1) {
            continue;
        }
        if (list[i] - number <= 3) {
            if (lookup.containsKey(list[i])) {
                permutations += lookup[list[i]]!!;
            }
            else {
                lookup[list[i]] = permutations(list[i], list.drop(i + 1));
                permutations += lookup[list[i]]!!;
            }
        }
    }
    return permutations;
}

fun main() {
    val inputList: List<Int> = File("input/day10.txt").readLines().map { it.toInt() }.sortedBy { it };
    val distribution: HashMap<Int, Int> = hashMapOf();
    var previous = 0;

    inputList.forEach {
        distribution[it - previous] = distribution.getOrDefault(it - previous, 0) + 1;
        previous = it;
    }
    distribution[3] = distribution.getOrDefault(3, 0) + 1;
    println(distribution.getOrDefault(1, 0) * distribution.getOrDefault(3, 0));

    println(permutations(0, inputList.plus(inputList.max()!! + 3)))
}