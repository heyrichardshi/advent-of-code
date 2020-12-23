import java.io.File

enum class Operation { ADD, MULTIPLY, PARENTHESIS }

val results: MutableList<Long> = mutableListOf()
val operandList: MutableList<Long> = mutableListOf()
val operationList: MutableList<Operation> = mutableListOf()

fun evaluate(a: Long, b: Long, op: Operation): Long {
    return when (op) {
        Operation.ADD -> a + b
        Operation.MULTIPLY -> a * b
        Operation.PARENTHESIS -> 0
    }
}

fun evaluateBack(operands: MutableList<Long>, operations: MutableList<Operation>) {
    val a = operands.removeAt(operands.size - 1)
    val b = operands.removeAt(operands.size - 1)
    val op = operations.removeAt(operations.size - 1)
    operands.add(evaluate(a, b, op))
}

fun evaluateFront() {
    val a = operandList.removeAt(0)
    val b = operandList.removeAt(0)
    val op = operationList.removeAt(0)
    operandList.add(0, evaluate(a, b, op))
}

fun evaluateParenthesis() {
    val parenOperandList: MutableList<Long> = mutableListOf()
    val parenOperationList: MutableList<Operation> = mutableListOf()
    while (operationList.last() != Operation.PARENTHESIS) {
        parenOperandList.add(operandList.removeAt(operandList.size - 1))
        parenOperationList.add(operationList.removeAt(operationList.size - 1))
    }
    parenOperandList.add(operandList.removeAt(operandList.size - 1))
    while (parenOperationList.isNotEmpty()) {
        evaluateBack(parenOperandList, parenOperationList)
    }
    operandList.add(parenOperandList.first())
    operationList.removeAt(operationList.size - 1)
}

fun evaluateOne(expressions: List<String>): Long {
    expressions.forEach { expr ->
        operandList.clear()
        operationList.clear()
        expr.split(" ").forEach {
            when (it) {
                "+" -> operationList.add(Operation.ADD)
                "*" -> operationList.add(Operation.MULTIPLY)
                else -> when {
                    it.startsWith("(") -> {
                        it.split("(").forEach { el ->
                            if (el.isEmpty()) { operationList.add(Operation.PARENTHESIS) }
                            else { operandList.add(el.toLong()) }
                        }
                    }
                    it.endsWith(")") -> {
                        it.split(")").forEach { el ->
                            if (el.isEmpty()) { evaluateParenthesis() }
                            else { operandList.add(el.toLong()) }
                        }
                    }
                    else -> operandList.add(it.toLong())
                }
            }
        }
        while (operandList.size != 1) {
            evaluateFront()
        }
        results.add(operandList.first())
    }
    return results.sum()
}

fun evaluateTwo(expressions: List<String>): Long {
    expressions.forEach { expr ->
        operandList.clear()
        operationList.clear()
        expr.split(" ").forEach {
            when (it) {
                "+" -> operationList.add(Operation.ADD)
                "*" -> operationList.add(Operation.MULTIPLY)
                else -> when {
                    it.startsWith("(") -> {
                        it.split("(").forEach { el ->
                            if (el.isEmpty()) { operationList.add(Operation.PARENTHESIS) }
                            else { operandList.add(el.toLong()) }
                        }
                    }
                    it.endsWith(")") -> {
                        it.split(")").forEach { el ->
                            if (el.isEmpty()) {
                                evaluateParenthesis()
                                if (operationList.isNotEmpty() && operationList.last() == Operation.ADD) {
                                    evaluateBack(operandList, operationList)
                                }
                            }
                            else {
                                operandList.add(el.toLong())
                                if (operationList.isNotEmpty() && operationList.last() == Operation.ADD) {
                                    evaluateBack(operandList, operationList)
                                }
                            }
                        }
                    }
                    else -> {
                        operandList.add(it.toLong())
                        if (operationList.isNotEmpty() && operationList.last() == Operation.ADD) {
                            evaluateBack(operandList, operationList)
                        }
                    }
                }
            }
        }
        if (operationList.isNotEmpty() && operationList.last() == Operation.ADD) {
            evaluateBack(operandList, operationList)
        }
        while (operandList.size != 1) {
            evaluateFront()
        }
        results.add(operandList.first())
    }
    return results.sum()
}

fun main() {
    val inputList: List<String> = File("input/day18.txt").readLines()

    println(evaluateOne(inputList))
    results.clear()

    println(evaluateTwo(inputList))
}