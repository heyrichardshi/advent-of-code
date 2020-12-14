import java.io.File
import kotlin.math.abs

enum class Direction { NORTH, SOUTH, EAST, WEST }

class Ferry {
    var direction = Direction.EAST;
    var xPosition = 0;
    var yPosition = 0;
    val waypoint: Waypoint = Waypoint();

    fun moveNorth(distance: Int) {
        yPosition += distance;
    }
    fun moveSouth(distance: Int) {
        yPosition -= distance;
    }
    fun moveEast(distance: Int) {
        xPosition += distance;
    }
    fun moveWest(distance: Int) {
        xPosition -= distance;
    }
    fun moveForward(distance: Int) {
        when (direction) {
            Direction.NORTH -> moveNorth(distance);
            Direction.SOUTH -> moveSouth(distance);
            Direction.EAST -> moveEast(distance);
            Direction.WEST -> moveWest(distance);
        }
    }
    fun rotateRight() {
        direction = when (direction) {
            Direction.NORTH -> Direction.EAST;
            Direction.SOUTH -> Direction.WEST;
            Direction.EAST -> Direction.SOUTH;
            Direction.WEST -> Direction.NORTH;
        }
    }
    fun rotateLeft() {
        direction = when (direction) {
            Direction.NORTH -> Direction.WEST;
            Direction.SOUTH -> Direction.EAST;
            Direction.EAST -> Direction.NORTH;
            Direction.WEST -> Direction.SOUTH;
        }
    }
    fun rotateRight(degrees: Int) {
        for (i in 1..(degrees / 90)) {
            rotateRight();
        }
    }
    fun rotateLeft(degrees: Int) {
        for (i in 1..(degrees / 90)) {
            rotateLeft();
        }
    }
    fun moveForwardToWaypoint(magnitude: Int) {
        xPosition += magnitude * waypoint.xPosition;
        yPosition += magnitude * waypoint.yPosition;
    }
}

class Waypoint() {
    var xPosition = 10;
    var yPosition = 1;

    fun moveNorth(distance: Int) {
        yPosition += distance;
    }
    fun moveSouth(distance: Int) {
        yPosition -= distance;
    }
    fun moveEast(distance: Int) {
        xPosition += distance;
    }
    fun moveWest(distance: Int) {
        xPosition -= distance;
    }
    fun rotateRight() {
        val newX = yPosition;
        val newY = -1 * xPosition;
        xPosition = newX;
        yPosition = newY;
    }
    fun rotateLeft() {
        val newX = -1 * yPosition;
        val newY = xPosition;
        xPosition = newX;
        yPosition = newY;
    }
    fun rotateRight(degrees: Int) {
        for (i in 1..(degrees / 90)) {
            rotateRight();
        }
    }
    fun rotateLeft(degrees: Int) {
        for (i in 1..(degrees / 90)) {
            rotateLeft();
        }
    }
}

fun main() {
    val inputList: List<String> = File("input/day12.txt").readLines();
    val lineFormat: Regex = "([NSEWLRF])(\\d+)".toRegex();

    val ferryOne: Ferry = Ferry();
    inputList.forEach {
        lineFormat.matchEntire(it)?.destructured?.let {
            (action, magnitude) -> when (action) {
                "N" -> ferryOne.moveNorth(magnitude.toInt());
                "S" -> ferryOne.moveSouth(magnitude.toInt());
                "E" -> ferryOne.moveEast(magnitude.toInt());
                "W" -> ferryOne.moveWest(magnitude.toInt());
                "L" -> ferryOne.rotateLeft(magnitude.toInt());
                "R" -> ferryOne.rotateRight(magnitude.toInt());
                "F" -> ferryOne.moveForward(magnitude.toInt());
                else -> {}
            }
        }
    }
    println(abs(ferryOne.xPosition) + abs(ferryOne.yPosition));

    val ferryTwo: Ferry = Ferry();
    inputList.forEach {
        lineFormat.matchEntire(it)?.destructured?.let {
                (action, magnitude) -> when (action) {
            "N" -> ferryTwo.waypoint.moveNorth(magnitude.toInt());
            "S" -> ferryTwo.waypoint.moveSouth(magnitude.toInt());
            "E" -> ferryTwo.waypoint.moveEast(magnitude.toInt());
            "W" -> ferryTwo.waypoint.moveWest(magnitude.toInt());
            "L" -> ferryTwo.waypoint.rotateLeft(magnitude.toInt());
            "R" -> ferryTwo.waypoint.rotateRight(magnitude.toInt());
            "F" -> ferryTwo.moveForwardToWaypoint(magnitude.toInt());
            else -> {}
        }
        }
    }
    println(abs(ferryTwo.xPosition) + abs(ferryTwo.yPosition));
}