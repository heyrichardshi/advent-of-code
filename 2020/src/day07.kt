import java.io.File

val bagRelations: HashMap<String, List<Pair<Int, String>>> = hashMapOf();
val outerBags: MutableSet<String> = mutableSetOf();
val lineFormat: Regex = "(.*) bags contain (.*)\\.".toRegex();
val bagFormat: Regex = "(\\d+) ([\\w ]+) bags?".toRegex();
const val myBagColor: String = "shiny gold";

fun addRelation(container: String, contained: String) {
    if (contained == "no other bags") {
        bagRelations[container] = listOf();
    }
    else {
        val containedList: MutableList<Pair<Int, String>> = mutableListOf();
        contained.split(", ").forEach {
            bagFormat.matchEntire(it)?.destructured?.let {
                (count, color) -> containedList.add(Pair(count.toInt(), color));
            }
        }
        bagRelations[container] = containedList;
        bagContains(container, myBagColor);
    }
}

fun bagContains(bagColor: String, targetColor: String) {
    bagRelations[bagColor]?.forEach {
        if (it.second == targetColor) {
            outerBags.add(bagColor);
            return;
        }
    }
}

fun countInnerBags(bagColor: String): Int {
    var bagCount = 0;
    bagRelations[bagColor]?.forEach { (count, color) ->
        bagCount += count * (countInnerBags(color) + 1);
    }
    return bagCount;
}

fun main() {
    val inputList: List<String> = File("input/day07.txt").readLines();
    
    for (line in inputList) {
        lineFormat.matchEntire(line)?.destructured?.let {
            (container, contained) -> addRelation(container, contained);
        }
    }

    var outerBagCount = 0;
    while (outerBagCount != outerBags.size) {
        val currentOuterBags: Set<String> = outerBags.toSet();
        outerBagCount = outerBags.size;
        bagRelations.keys.forEach { bagColor ->
            currentOuterBags.forEach { outerBagColor ->
                bagContains(bagColor, outerBagColor);
            }
        }
    }
    println(outerBagCount);

    println(countInnerBags(myBagColor));
}