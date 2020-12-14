import java.io.File

val busIndexList: MutableList<Int> = mutableListOf();
val indexedBuses: HashMap<Int, Int> = hashMapOf();
var leastCommonMultiple: Long = 0;
var currentListIndex = 1;

fun gcd(a: Long, b: Long): Long {
    val (larger: Long, smaller: Long) = if (a > b) { Pair(a, b) } else { Pair(b, a) };
    if (smaller == 0L) {
        return larger;
    }
    return gcd(smaller, larger % smaller);
}

fun lcm(a: Long, b: Long): Long {
    return a / gcd(a, b) * b;
}

fun seekTime(): Long {
    var t: Long = indexedBuses[busIndexList[0]]!!.toLong();
    leastCommonMultiple = t;
    while (!seekTime(t)) {
        t += leastCommonMultiple;
    }
    return t;
}

fun seekTime(timestamp: Long): Boolean {
    if (currentListIndex == busIndexList.size) {
        return true;
    }
    val offset: Int = busIndexList[currentListIndex];
    val targetTime: Long = timestamp + offset;
    val busID: Long = indexedBuses[busIndexList[currentListIndex]]!!.toLong();

    if (targetTime % busID == 0L) {
        leastCommonMultiple = lcm(leastCommonMultiple, busID);
        currentListIndex++;
        return seekTime(timestamp);
    }
    return false;
}

fun main() {
    val inputList: List<String> = File("input/day13.txt").readLines();
    val arrivalTime = inputList[0].toInt();
    val busList: List<String> = inputList[1].split(",");

    val buses: List<Int> = busList.filter { it != "x" }.map { it.toInt() }
    val nearestBuses: HashMap<Int, Int> = hashMapOf();
    buses.forEach { bus ->
        nearestBuses[bus] = (arrivalTime / bus) * bus + bus;
    }
    val soonestBus = nearestBuses.minBy { it.value }!!
    println(soonestBus.key * (soonestBus.value - arrivalTime))

    busList.indices.forEach {
        if (busList[it] != "x") {
            busIndexList.add(it);
            indexedBuses[it] = busList[it].toInt();
        }
    }
    println(seekTime());
}