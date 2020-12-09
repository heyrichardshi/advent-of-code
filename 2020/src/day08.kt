import java.io.File

var accumulator = 0;
val instructionExecutionCount: HashMap<Int, Boolean> = hashMapOf();
val jmpInstructions: MutableList<Int> = mutableListOf();
val nopInstructions: MutableList<Int> = mutableListOf();

fun checkLoop(instruction: Int): Boolean {
    if (instructionExecutionCount[instruction] == true) {
        return true;
    }
    instructionExecutionCount[instruction] = true;
    return false;
}

fun clearInstructionCounts() {
    for (entry in instructionExecutionCount) {
        instructionExecutionCount[entry.key] = false;
    }
}

fun runProgram(program: List<String>): Boolean {
    accumulator = 0;
    var instruction = 0;
    while (instruction != program.size) {
        if (checkLoop(instruction)) {
            return false;
        }
        program[instruction].split(" ").let {
            when (it.first()) {
                "acc" -> accumulator += it.last().toInt();
                "jmp" -> instruction += it.last().toInt();
                else -> {}
            }
            if (it.first() != "jmp") {
                instruction++;
            }
        }
    }
    return true;
}

fun main() {
    val inputList: List<String> = File("input/day08.txt").readLines();

    inputList.indices.forEach {
        instructionExecutionCount[it] = false;
        if (inputList[it].contains("jmp")) {
            jmpInstructions.add(it)
        }
        else if (inputList[it].contains("nop")) {
            nopInstructions.add(it)
        }
    }

    if (!runProgram(inputList)) {
        println(accumulator);
    }

    jmpInstructions.forEach {
        val newProgram: MutableList<String> = mutableListOf();
        newProgram.addAll(inputList.dropLast(inputList.size - it))
        newProgram.add(inputList[it].replace("jmp", "nop"))
        newProgram.addAll(inputList.drop(it + 1))

        clearInstructionCounts();
        if (runProgram(newProgram)) {
            println(accumulator);
            return;
        }
    }

    nopInstructions.forEach {
        val newProgram: MutableList<String> = mutableListOf();
        newProgram.addAll(inputList.dropLast(inputList.size - it))
        newProgram.add(inputList[it].replace("nop", "jmp"))
        newProgram.addAll(inputList.drop(it + 1))

        clearInstructionCounts();
        if (runProgram(newProgram)) {
            println(accumulator);
            return;
        }
    }
}