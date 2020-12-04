import java.io.File

fun main() {
    val inputList: List<String> = File("input/day03.txt").readLines();
    val slopeRightOne: Int = 1;
    val slopeRightTwo: Int = 3;
    val slopeRightThree: Int = 5;
    val slopeRightFour: Int = 7;
    val slopeDownFive: Int = 2;
    var positionOne: Int = 0;
    var positionTwo: Int = 0;
    var positionThree: Int = 0;
    var positionFour: Int = 0;
    var positionFive: Int = 0;
    var numTreesOne: Long = 0;
    var numTreesTwo: Long = 0;
    var numTreesThree: Long = 0;
    var numTreesFour: Long = 0;
    var numTreesFive: Long = 0;

    for (line in inputList) {
        if (line[positionOne % line.length] == '#') {
            numTreesOne += 1;
        }
        if (line[positionTwo % line.length] == '#') {
            numTreesTwo += 1;
        }
        if (line[positionThree % line.length] == '#') {
            numTreesThree += 1;
        }
        if (line[positionFour % line.length] == '#') {
            numTreesFour += 1;
        }
        if (positionOne % slopeDownFive == 0) {
            if (line[positionFive % line.length] == '#') {
                numTreesFive += 1;
            }
            positionFive += 1;
        }
        positionOne += slopeRightOne;
        positionTwo += slopeRightTwo;
        positionThree += slopeRightThree;
        positionFour += slopeRightFour;
    }

    println(numTreesTwo);
    println(numTreesOne * numTreesTwo * numTreesThree * numTreesFour * numTreesFive);
}