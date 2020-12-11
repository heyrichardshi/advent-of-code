import java.io.File

var changes: Boolean = false;

fun evaluatePositionOne(state: List<List<String>>, row: Int, col: Int): String {
    if (state[row][col] == ".") {
        return "."
    }
    var occupiedSeats = 0;
    for (i in -1..1) {
        for (j in -1..1) {
            if ((i == 0 && j == 0) ||
                (row + i < 0) || (col + j < 0) ||
                (row + i >= state.size) || (col + j >= state[row].size)) {
                continue;
            }
            if (state[row + i][col + j] == "#") {
                occupiedSeats++;
            }
        }
    }
    if ((state[row][col] == "L") && (occupiedSeats == 0)) {
        changes = true;
        return "#"
    }
    if ((state[row][col] == "#") && (occupiedSeats >= 4)) {
        changes = true;
        return "L"
    }
    return state[row][col]
}

fun seesOccupiedSeat(state: List<List<String>>, row: Int, col: Int, i: Int, j: Int): Boolean {
    if (row !in state.indices || col !in state[row].indices) {
        return false;
    }
    return when (state[row][col]) {
        "#" -> true;
        "L" -> false;
        else -> seesOccupiedSeat(state, row + i, col + j, i, j);
    }
}

fun evaluatePositionTwo(state: List<List<String>>, row: Int, col: Int): String {
    if (state[row][col] == ".") {
        return "."
    }
    var occupiedSeats = 0;
    for (i in -1..1) {
        for (j in -1..1) {
            if ((i == 0 && j == 0) ||
                (row + i < 0) || (col + j < 0) ||
                (row + i >= state.size) || (col + j >= state[row].size)) {
                continue;
            }
            if (seesOccupiedSeat(state, row + i, col + j, i, j)) {
                occupiedSeats++;
            }
        }
    }
    if ((state[row][col] == "L") && (occupiedSeats == 0)) {
        changes = true;
        return "#"
    }
    if ((state[row][col] == "#") && (occupiedSeats >= 5)) {
        changes = true;
        return "L"
    }
    return state[row][col]
}

fun evaluateStateOne(state: List<List<String>>): List<List<String>> {
    return state.indices.map { i ->
        state[i].indices.map { j ->
            evaluatePositionOne(state, i, j);
        }
    }
}

fun evaluateStateTwo(state: List<List<String>>): List<List<String>> {
    return state.indices.map { i ->
        state[i].indices.map { j ->
            evaluatePositionTwo(state, i, j);
        }
    }
}

fun countOccupiedSeats(state: List<List<String>>): Int {
    var occupiedSeats = 0;
    state.forEach { row ->
        row.forEach {
            if (it == "#") {
                occupiedSeats++;
            }
        }
    }
    return occupiedSeats;
}

fun main() {
    val inputList: List<List<String>> = File("input/day11.txt").readLines().map { it.split("") }

    var state: List<List<String>> = inputList;
    do {
        changes = false;
        state = evaluateStateOne(state);
    } while (changes);
    println(countOccupiedSeats(state));

    state = inputList;
    do {
        changes = false;
        state = evaluateStateTwo(state);
    } while (changes);
    println(countOccupiedSeats(state));
}