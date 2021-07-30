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

    fun validate(input: String): Boolean {
        if (cachedRegex.isEmpty()) {
            cachedRegex.addAll(getRule().map { it.toRegex() })
        }
        for (r in cachedRegex) {
            if (r.matches(input)) { return true }
        }
        return false
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
}