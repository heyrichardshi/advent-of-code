import java.io.File

var bitmask: String = "";
val memory: HashMap<Long, Long> = hashMapOf();

fun applyBitmaskV1(value: Long): Long {
    val paddedValue: String = value.toString(2).padStart(36, '0')
    val maskedValue: String = paddedValue.indices.map {
        if (bitmask[it] == 'X') { paddedValue[it] } else { bitmask[it] }
    }.joinToString("");
    return maskedValue.toLong(2);
}

fun applyBitmaskV2(address: Long): List<Long> {
    val paddedAddress: String = address.toString(2).padStart(36, '0')
    val maskedAddress: String = paddedAddress.indices.map {
        when (bitmask[it]) {
            '0' -> paddedAddress[it];
            '1' -> '1';
            else -> 'X';
        }
    }.joinToString("");
    return evaluateMaskedAddress(maskedAddress).map {
        it.toLong(2);
    }
}

fun evaluateMaskedAddress(address: String): List<String> {
    if (address.isBlank()) {
        return listOf("");
    }
    val potentialValues: List<String> = when (address[0]) {
        '0' -> listOf("0");
        '1' -> listOf("1");
        else -> listOf("0", "1")
    }
    return potentialValues.flatMap { head ->
        evaluateMaskedAddress(address.drop(1)).map { tail ->
            head + tail;
        }
    }
}

fun main() {
    val inputList: List<String> = File("input/day14.txt").readLines();
    val bitmaskUpdateFormat: Regex = "mask = ([X10]*)".toRegex();
    val writeMemoryFormat: Regex = "mem\\[(\\d+)\\] = (\\d+)".toRegex();

    inputList.forEach {
        if (it.startsWith("mask")) {
            bitmaskUpdateFormat.matchEntire(it)?.destructured?.let { (mask) ->
                bitmask = mask;
            }
        }
        else {
            writeMemoryFormat.matchEntire(it)?.destructured?.let { (location, value) ->
                memory[location.toLong()] = applyBitmaskV1(value.toLong());
            }
        }
    }
    println(memory.values.sum())

    memory.clear();
    inputList.forEach {
        if (it.startsWith("mask")) {
            bitmaskUpdateFormat.matchEntire(it)?.destructured?.let { (mask) ->
                bitmask = mask;
            }
        }
        else {
            writeMemoryFormat.matchEntire(it)?.destructured?.let { (location, value) ->
                applyBitmaskV2(location.toLong()).forEach { address ->
                    memory[address] = value.toLong();
                }
            }
        }
    }
    println(memory.values.sum())
}