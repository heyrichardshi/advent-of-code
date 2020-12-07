import java.io.File

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