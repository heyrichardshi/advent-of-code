import java.io.File

//val totalRows: List<Int> = (0..127).toList();
//val totalColumns: List<Int> = (0..7).toList();
//val seats: HashMap<Pair<Int, Int>, Boolean> = hashMapOf();
//val seatIds: HashMap<Int, Boolean> = hashMapOf();
//
//fun processRow(rowString: String, rows: List<Int>): Int {
//    return when {
//        rowString.isEmpty() -> {
//            rows.first();
//        }
//        rowString.first() == 'F' -> {
//            processRow(rowString.drop(1), rows.dropLast(rows.size / 2));
//        }
//        else -> {
//            processRow(rowString.drop(1), rows.drop(rows.size / 2));
//        }
//    }
//}
//
//fun processColumn(columnString: String, columns: List<Int>): Int {
//    return when {
//        columnString.isEmpty() -> {
//            columns.first();
//        }
//        columnString.first() == 'L' -> {
//            processColumn(columnString.drop(1), columns.dropLast(columns.size / 2));
//        }
//        else -> {
//            processColumn(columnString.drop(1), columns.drop(columns.size / 2));
//        }
//    }
//}
//
//fun getSeat(rowString: String, columnString: String): Pair<Int, Int> {
//    val seat: Pair<Int, Int> = Pair(
//            processRow(rowString, totalRows),
//            processColumn(columnString, totalColumns)
//    );
//    seats[seat] = true;
//    return seat;
//}
//
//fun seatID(seat: Pair<Int, Int>): Int {
//    return seat.first * 8 + seat.second;
//}

val formOne: HashMap<Char, Boolean> = hashMapOf();
val formTwo: MutableList<String> = mutableListOf();
var sumOfYesOne = 0;
var sumOfYesTwo = 0;

fun clearForms() {
    sumOfYesOne += countFormOneYes();
    sumOfYesTwo += countFormTwoYes();
    "abcdefghijklmnopqrstuvwxyz".forEach {
        formOne[it] = false;
    }
    formTwo.clear();
}

fun addToFormOne(answers: String) {
    answers.forEach {
        formOne[it] = true;
    }
}

fun countFormOneYes(): Int {
    return formOne.count { it.value }
}

fun countFormTwoYes(): Int {
    var count = 0;
    "abcdefghijklmnopqrstuvwxyz".forEach {
        if (formTwo.all { s -> s.contains(it) }) {
            count++;
        }
    }
    return count;
}

fun main() {
    val inputList: List<String> = File("input/day06.txt").readLines();
    
    for (line in inputList) {
        if (line.isEmpty()) {
            clearForms();
        }
        else {
            addToFormOne(line);
            formTwo.add(line);
        }
    }
    clearForms();
    
    println(sumOfYesOne);
    println(sumOfYesTwo);
}