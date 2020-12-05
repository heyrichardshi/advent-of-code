import java.io.File

val requiredFields: List<String> = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
val optionalFields: List<String> = listOf("cid");
val currentFields: HashMap<String, String> = hashMapOf();

fun clearCurrentFields() {
    for (field in requiredFields) {
        currentFields[field] = "";
    }
    for (field in optionalFields) {
        currentFields.remove(field);
    }
}

fun checkFieldsExist(): Boolean {
    return currentFields.all { it.value.isNotEmpty() }
}

fun validateData(field: String, data: String): Boolean {
    return when (field) {
        "byr" -> (data.length == 4) && (data.toInt() in 1920..2002);
        "iyr" -> (data.length == 4) && (data.toInt() in 2010..2020);
        "eyr" -> (data.length == 4) && (data.toInt() in 2020..2030);
        "hgt" -> (
                ("^\\d{3}cm$".toRegex().matches(data)) && (data.split("cm")[0].toInt() in 150..193)) ||
                (("^\\d{2}in$".toRegex().matches(data)) && (data.split("in")[0].toInt() in 59..76));
        "hcl" -> "^#[a-f0-9]{6}$".toRegex().matches(data);
        "ecl" -> data in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        "pid" -> "^\\d{9}$".toRegex().matches(data);
        "cid" -> true;
        else -> {
            false;
        }
    }
}

fun checkFieldsValid(): Boolean {
    return currentFields.all { validateData(it.key, it.value) };
}

fun main() {
    val inputList: List<String> = File("input/day04.txt").readLines();
    var numValidPassportsOne = 0;
    var numValidPassportsTwo = 0; // with data validation

    clearCurrentFields();

    for (line in inputList) {
        if (line.isEmpty()) {
            if (checkFieldsExist()) {
                numValidPassportsOne += 1;
            }
            if (checkFieldsValid()) {
                numValidPassportsTwo += 1;
            }
            clearCurrentFields();
            continue;
        }
        for (field in line.split(" ")) {
            val fieldList = field.split(":");
            currentFields[fieldList.first()] = fieldList.last();
        }
    }

    if (checkFieldsExist()) {
        numValidPassportsOne += 1;
    }
    if (checkFieldsValid()) {
        numValidPassportsTwo += 1;
    }
    clearCurrentFields();
    println(numValidPassportsOne);
    println(numValidPassportsTwo);
}