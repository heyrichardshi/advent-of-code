import java.io.File

val xCoordinates: MutableSet<Int> = mutableSetOf();
val yCoordinates: MutableSet<Int> = mutableSetOf();
val zCoordinates: MutableSet<Int> = mutableSetOf();
val wCoordinates: MutableSet<Int> = mutableSetOf();

data class Quadruple(
        val x: Int,
        val y: Int,
        val z: Int,
        val w: Int
)

fun evaluatePosition3D(space: HashMap<Triple<Int, Int, Int>, Boolean>, x: Int, y: Int, z: Int): Boolean {
    var active = 0
    for (i in -1..1) {
        for (j in -1..1) {
            for (k in -1..1) {
                if ((i == 0) && (j == 0) && (k == 0)) {
                    continue
                }
                if (space.getOrDefault(Triple(x + i, y + j, z + k), false)) {
                    active++
                }
            }
        }
    }
    val currentState: Boolean = space.getOrDefault(Triple(x, y, z), false)
    if (currentState) {
        if (active !in 2..3) {
            return false
        }
    }
    else if (active == 3) {
        return true
    }
    return currentState
}

fun evaluateSpace3D(space: HashMap<Triple<Int, Int, Int>, Boolean>): HashMap<Triple<Int, Int, Int>, Boolean> {
    val newSpace: HashMap<Triple<Int, Int, Int>, Boolean> = hashMapOf()
    for (x in (xCoordinates.min()!! - 1)..(xCoordinates.max()!! + 1)) {
        for (y in (yCoordinates.min()!! - 1)..(yCoordinates.max()!! + 1)) {
            for (z in (zCoordinates.min()!! - 1)..(zCoordinates.max()!! + 1)) {
                newSpace[Triple(x, y, z)] = evaluatePosition3D(space, x, y, z)
            }
        }
    }
    expandCoordinates()
    return newSpace
}

fun countActives3D(space: HashMap<Triple<Int, Int, Int>, Boolean>): Int {
    return space.count { it.value }
}

fun evaluatePosition4D(space: HashMap<Quadruple, Boolean>, x: Int, y: Int, z: Int, w: Int): Boolean {
    var active = 0
    for (i in -1..1) {
        for (j in -1..1) {
            for (k in -1..1) {
                for (l in -1..1) {
                    if ((i == 0) && (j == 0) && (k == 0) && (l == 0)) {
                        continue
                    }
                    if (space.getOrDefault(Quadruple(x + i, y + j, z + k, w + l), false)) {
                        active++
                    }
                }
            }
        }
    }
    val currentState: Boolean = space.getOrDefault(Quadruple(x, y, z, w), false)
    if (currentState) {
        if (active !in 2..3) {
            return false
        }
    }
    else if (active == 3) {
        return true
    }
    return currentState
}

fun evaluateSpace4D(space: HashMap<Quadruple, Boolean>): HashMap<Quadruple, Boolean> {
    val newSpace: HashMap<Quadruple, Boolean> = hashMapOf()
    for (x in (xCoordinates.min()!! - 1)..(xCoordinates.max()!! + 1)) {
        for (y in (yCoordinates.min()!! - 1)..(yCoordinates.max()!! + 1)) {
            for (z in (zCoordinates.min()!! - 1)..(zCoordinates.max()!! + 1)) {
                for (w in (wCoordinates.min()!! - 1)..(wCoordinates.max()!! + 1)) {
                    newSpace[Quadruple(x, y, z, w)] = evaluatePosition4D(space, x, y, z, w)
                }
            }
        }
    }
    expandCoordinates(true)
    return newSpace
}

fun countActives4D(space: HashMap<Quadruple, Boolean>): Int {
    return space.count { it.value }
}

fun expandCoordinates(includeW: Boolean = false) {
    xCoordinates.add(xCoordinates.min()!! - 1)
    xCoordinates.add(xCoordinates.max()!! + 1)
    yCoordinates.add(yCoordinates.min()!! - 1)
    yCoordinates.add(yCoordinates.max()!! + 1)
    zCoordinates.add(zCoordinates.min()!! - 1)
    zCoordinates.add(zCoordinates.max()!! + 1)
    if (includeW) {
        wCoordinates.add(wCoordinates.min()!! - 1)
        wCoordinates.add(wCoordinates.max()!! + 1)
    }
}

fun main() {
    val inputList: List<List<String>> = File("input/day17.txt").readLines().map { it.split("") }

    var space3D: HashMap<Triple<Int, Int, Int>, Boolean> = hashMapOf()
    inputList.indices.forEach { y ->
        inputList[y].indices.forEach { x ->
            space3D[Triple(x, y, 0)] = when (inputList[y][x]) {
                "#" -> true
                else -> false
            }
            xCoordinates.add(x)
        }
        yCoordinates.add(y)
    }
    zCoordinates.add(0)

    repeat(6) {
        space3D = evaluateSpace3D(space3D)
    }
    println(countActives3D(space3D))

    var space4D: HashMap<Quadruple, Boolean> = hashMapOf()
    xCoordinates.clear()
    yCoordinates.clear()
    zCoordinates.clear()
    wCoordinates.clear()
    inputList.indices.forEach { y ->
        inputList[y].indices.forEach { x ->
            space4D[Quadruple(x, y, 0, 0)] = when (inputList[y][x]) {
                "#" -> true
                else -> false
            }
            xCoordinates.add(x)
        }
        yCoordinates.add(y)
    }
    zCoordinates.add(0)
    wCoordinates.add(0)

    repeat(6) {
        space4D = evaluateSpace4D(space4D)
    }
    println(countActives4D(space4D))
}