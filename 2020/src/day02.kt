import java.io.File

data class PasswordPolicy(
    val lowerLimit: Int,
    val upperLimit: Int,
    val character: Char
)

fun validatePasswordOne(password: String, policy: PasswordPolicy): Boolean {
    return password.map { it == policy.character }.count { it } in policy.lowerLimit..policy.upperLimit;
}

fun validatePasswordTwo(password: String, policy: PasswordPolicy): Boolean {
    return (password[policy.lowerLimit - 1] == policy.character) xor (password[policy.upperLimit - 1] == policy.character);
}

fun main() {
    val inputList: List<String> = File("input/day02.txt").readLines();
    val lineFormat: Regex = "(\\d+)-(\\d+) ([\\w]): (\\w+)".toRegex();
    var numValidPasswordsOne: Int = 0;
    var numValidPasswordsTwo: Int = 0;

    for (line in inputList) {
        if (lineFormat.matchEntire(line)?.destructured?.let { (min, max, char, password) ->
                    validatePasswordOne(password, PasswordPolicy(min.toInt(), max.toInt(), char.single()))
                }!!) {
            numValidPasswordsOne += 1;
        }
        if (lineFormat.matchEntire(line)?.destructured?.let { (min, max, char, password) ->
                    validatePasswordTwo(password, PasswordPolicy(min.toInt(), max.toInt(), char.single()))
                }!!) {
            numValidPasswordsTwo += 1;
        }
    }
    println(numValidPasswordsOne);
    println(numValidPasswordsTwo);
}