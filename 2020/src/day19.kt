import java.io.File

val rules: HashMap<Int, Rule> = hashMapOf()

fun getRuleOf(ruleNumber: Int): Set<String> {
    return rules[ruleNumber]!!.getRule()
}

open class Rule(match: String) {
    private var matchString: String = ""
    private val matchRules: MutableList<List<Int>> = mutableListOf()
    val cachedRules: MutableSet<String> = mutableSetOf()
    private val cachedRegex: MutableList<Regex> = mutableListOf()

    init {
        if (match.startsWith("\"")) {
            matchString = match.split("\"").filter { it.isNotEmpty() }[0]
        }
        else {
            match.split(" | ").forEach {
                matchRules.add(it.split(" ").map { n -> n.toInt() })
            }
        }
    }

    open fun getRule(): Set<String> {
        when {
            cachedRules.isNotEmpty() -> {}
            matchString.isNotEmpty() -> {
                cachedRules.add(matchString)
            }
            else -> {
                matchRules.forEach { ruleGroup ->
                    cachedRules.addAll (
                            if (ruleGroup.size == 1) {
                                getRuleOf(ruleGroup.first())
                            }
                            else {
                                getRuleOf(ruleGroup.first()).flatMap { pre ->
                                    getRuleOf(ruleGroup.last()).map { post ->
                                        pre + post
                                    }
                                }
                            }
                    )
                }
            }
        }
        return cachedRules
    }

    open fun validate(input: String): Boolean {
        if (cachedRegex.isEmpty()) {
            cachedRegex.addAll(getRule().map { it.toRegex() })
        }
        for (r in cachedRegex) {
            if (r.matches(input)) { return true }
        }
        return false
    }
}

// special handling for part 2 due to presence of loops
// all patterns are of length 8 -> easier validation logic
class Rule0(match: String): Rule(match) {
    override fun validate(input: String): Boolean {
        if (input.length % 8 != 0) {
            return false
        }

        val sections: Int = input.length / 8

        // rule 0 must match both rule 8 and rule 11
        // rule 8 must match at least 1 group of 8; rule 11 must match at least 2 groups of 8
        // therefore, rule 0 must match at least 3 groups of 8
        if (sections < 3) {
            return false
        }

        for (i in (if (sections % 2 == 0) 2 else 1)..(sections - 2) step 2) {
            if (rules[8]!!.validate(input.take(i * 8)) &&
                    rules[11]!!.validate(input.takeLast((sections - i) * 8))) {
                return true
            }
        }

        return false
    }
}

class Rule8(match: String): Rule(match) {
    override fun validate(input: String): Boolean {
        return input.chunked(8).map { rules[42]!!.validate(it) }.all { it }
    }
}

class Rule11(match: String): Rule(match) {
    override fun validate(input: String): Boolean {
        val sectionSize: Int = input.length / 2
        val input42: String = input.take(sectionSize)
        val input31: String = input.takeLast(sectionSize)

        return input42.chunked(8).map { rules[42]!!.validate(it) }.all { it } &&
                input31.chunked(8).map { rules[31]!!.validate(it) }.all { it }
    }
}

fun main() {
    val inputList: List<String> = File("input/day19.txt").readLines()
    val ruleFormat: Regex = "(\\d+): (.+)".toRegex()
    var rulesSection = true
    var numberValidMessages = 0

    inputList.forEach { line ->
        when {
            line.isEmpty() -> rulesSection = false
            rulesSection -> {
                ruleFormat.matchEntire(line)?.destructured?.let {
                    (ruleNumber, rule) -> rules[ruleNumber.toInt()] = Rule(rule)
                }
            }
            else -> {
                if (rules[0]!!.validate(line)) {
                    numberValidMessages++
                }
            }
        }
    }
    println(numberValidMessages)

    rules.clear()
    rulesSection = true
    numberValidMessages = 0
    inputList.forEach { line ->
        when {
            line.isEmpty() -> rulesSection = false
            rulesSection -> {
                ruleFormat.matchEntire(line)?.destructured?.let {
                    (ruleNumber, rule) -> rules[ruleNumber.toInt()] = when (ruleNumber.toInt()) {
                        0 -> Rule0(rule)
                        8 -> Rule8(rule)
                        11 -> Rule11(rule)
                        else -> Rule(rule)
                    }
                }
            }
            else -> {
                if (rules[0]!!.validate(line)) {
                    numberValidMessages++
                }
            }
        }
    }
    println(numberValidMessages)
}