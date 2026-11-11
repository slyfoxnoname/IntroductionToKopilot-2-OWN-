package hm3

fun countCharOccurrences(text: String): Map<Char, Int> {
    val stats = mutableMapOf<Char, Int>()
    for (symbol in text) {
        val previous = stats.getOrDefault(symbol, 0)
        stats[symbol] = previous + 1
    }
    return stats
}

fun main() {
    val sampleSentence = "kotlin collections and lambdas"
    val charDistribution = countCharOccurrences(sampleSentence)

    println("Статистика символів для рядка \"$sampleSentence\":")
    for ((ch, freq) in charDistribution) {
        val printedSymbol = if (ch == ' ') "[space]" else ch.toString()
        println("  $printedSymbol -> $freq")
    }
}
