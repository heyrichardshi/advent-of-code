import java.io.File

val totalRows: List<Int> = (0..127).toList();
val totalColumns: List<Int> = (0..7).toList();
val seats: HashMap<Pair<Int, Int>, Boolean> = hashMapOf();
val seatIds: HashMap<Int, Boolean> = hashMapOf();

fun processRow(rowString: String, rows: List<Int>): Int {
    return when {
        rowString.isEmpty() -> {
            rows.first();
        }
        rowString.first() == 'F' -> {
            processRow(rowString.drop(1), rows.dropLast(rows.size / 2));
        }
        else -> {
            processRow(rowString.drop(1), rows.drop(rows.size / 2));
        }
    }
}

fun processColumn(columnString: String, columns: List<Int>): Int {
    return when {
        columnString.isEmpty() -> {
            columns.first();
        }
        columnString.first() == 'L' -> {
            processColumn(columnString.drop(1), columns.dropLast(columns.size / 2));
        }
        else -> {
            processColumn(columnString.drop(1), columns.drop(columns.size / 2));
        }
    }
}

fun getSeat(rowString: String, columnString: String): Pair<Int, Int> {
    val seat: Pair<Int, Int> = Pair(
            processRow(rowString, totalRows),
            processColumn(columnString, totalColumns)
    );
    seats[seat] = true;
    return seat;
}

fun seatID(seat: Pair<Int, Int>): Int {
    return seat.first * 8 + seat.second;
}

fun main() {
    val inputList: List<String> = File("input/day05.txt").readLines();
    val lineFormat: Regex = "([FB]{7})([LR]{3})".toRegex();
    var highestSeatId = 0;

    totalRows.forEach { i -> totalColumns.forEach { j -> seats[Pair(i, j)] = false } }

    for (line in inputList) {
        val seatID = lineFormat.matchEntire(line)?.destructured?.let {
            (rowPartition, columnPartition) -> seatID(getSeat(rowPartition, columnPartition))
        } ?: 0;
        seatIds[seatID] = true;
        if (seatID > highestSeatId) {
            highestSeatId = seatID;
        }
    }

    println(highestSeatId);

    seats.filter { (_, filled) -> !filled }.forEach { (seat, _) ->
        val thisId = seatID(seat);
        if (seatIds.getOrDefault(thisId + 1, false) && seatIds.getOrDefault(thisId - 1, false)) {
            println(thisId);
        }
    }
}