package hm3

import kotlin.random.Random

fun main() {
    val sourceData = Array(100) { Random.nextInt(-100, 101) }

    val filteredSquares = sourceData
        .asSequence()
        .filter { value -> value > 0 }
        .filter { value -> value % 5 == 0 }
        .map { value -> value * value }
        .sortedDescending()
        .toList()

    if (filteredSquares.isNotEmpty()) {
        val maxVal = filteredSquares.first()
        val minVal = filteredSquares.last()
        println("Найбільший квадрат у вибірці: $maxVal")
        println("Найменший квадрат у вибірці:  $minVal")
    } else {
        println("Після фільтрації жодного елемента не залишилося.")
    }

    val stringItems: List<String> = filteredSquares.map { num -> "$num" }
    val singleLine = stringItems.joinToString(" ")
    println("Підсумковий рядок значень: $singleLine")
}
