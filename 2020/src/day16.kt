import java.io.File

val constraints: HashMap<String, Pair<IntRange, IntRange>> = hashMapOf()
val potentialFields: HashMap<String, MutableList<Int>> = hashMapOf()
val fields: HashMap<String, Int> = hashMapOf()

fun validateTicket(ticket: List<Int>): List<Int> {
    val invalidValues: MutableList<Int> = mutableListOf()
    ticket.forEach {
        if (!constraints.values.map {constraint -> (it in constraint.first) || (it in constraint.second)}.contains(true)) {
            invalidValues.add(it)
        }
    }
    return invalidValues
}

fun identifyFields(validTickets: List<List<Int>>) {
    constraints.forEach { field, range ->
        validTickets[0].indices.forEach { i ->
            if (validTickets.all { (it[i] in range.first) || (it[i] in range.second) }) {
                if (potentialFields.containsKey(field)) {
                    potentialFields[field]!!.add(i)
                }
                else {
                    potentialFields[field] = mutableListOf(i)
                }
            }
        }
    }
    while (fields.size != potentialFields.size) {
        val single: Pair<String, Int> = findSingle()
        fields[single.first] = single.second
    }
}

fun findSingle(): Pair<String, Int> {
    var single: Pair<String, Int> = Pair("", 0)
    for ((field, possibilities) in potentialFields) {
        if (possibilities.size == 1) {
            single = Pair(field, possibilities.first())
            break
        }
    }
    for ((_, possibilities) in potentialFields) {
        possibilities.remove(single.second)
    }
    return single
}

fun main() {
    val inputList: List<String> = File("input/day16.txt").readLines()
    val constraintsFormat: Regex = "([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex()
    var yourTicketNext: Boolean = false
    var nearbyTicketsNext: Boolean = false
    val validTickets: MutableList<List<Int>> = mutableListOf();
    val invalidValues: MutableList<Int> = mutableListOf();
    var yourTicket: List<Int> = listOf();

    for (line in inputList) {
        if (line.isEmpty()) { continue }
        if (line == "your ticket:") { yourTicketNext = true; continue }
        if (line == "nearby tickets:") { nearbyTicketsNext = true; continue }
        if (!yourTicketNext && !nearbyTicketsNext) {
            constraintsFormat.matchEntire(line)?.destructured?.let {
                (constraint, range1Lower, range1Upper, range2Lower, range2Upper) ->
                constraints[constraint] = Pair(range1Lower.toInt()..range1Upper.toInt(), range2Lower.toInt()..range2Upper.toInt())
            }
        }
        else if (yourTicketNext) {
            yourTicketNext = false
            yourTicket = line.split(",").map { it.toInt() }
        }
        else if (nearbyTicketsNext) {
            val thisTicket: List<Int> = line.split(",").map { it.toInt() }
            val theseInvalidValues: List<Int> = validateTicket(thisTicket)
            if (theseInvalidValues.isEmpty()) {
                validTickets.add(thisTicket)
            }
            else {
                invalidValues.addAll(theseInvalidValues)
            }
        }
    }
    println(invalidValues.sum())

    var product: Long = 1
    identifyFields(validTickets)
    fields.filterKeys { it.startsWith("departure") }.forEach { (_, fieldIndex) ->
        product *= yourTicket[fieldIndex]
    }
    println(product)
}