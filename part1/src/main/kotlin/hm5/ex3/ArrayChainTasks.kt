package hm5.ex3

import kotlin.random.Random

data class ArraySummary(
    val maxElement: Int,
    val elementsOverLimit: Int,
    val totalSum: Int
)

fun generateRandomArray(size: Int, maxValue: Int): IntArray {
    if (size <= 0 || maxValue <= 0) {
        throw IllegalArgumentException("Кількість елементів і максимум мають бути більшими за 0")
    }
    return IntArray(size) { Random.nextInt(1, maxValue + 1) }
}

fun main() {
    generateRandomArray(size = 8, maxValue = 40)
        .also { source ->
            println("Вхідні дані масиву:  ${source.contentToString()}")
        }
        .apply {
            for (pos in indices) {
                val elem = this[pos]
                this[pos] = if (elem % 2 == 0) elem / 2 else elem * 2
            }
        }
        .also { mutated ->
            println("Оновлені дані масиву: ${mutated.contentToString()}")
        }
        .run {
            ArraySummary(
                maxElement = this.max(),
                elementsOverLimit = this.count { value -> value > 20 },
                totalSum = this.sum()
            )
        }
        .let { summary ->
            """
            +----------------------------------------+
            | АНАЛІТИЧНИЙ ЗВІТ ОБРОБКИ МАСИВУ        |
            +----------------------------------------+
            | Найбільший елемент       : ${summary.maxElement}
            | Елементів зі значенням >20: ${summary.elementsOverLimit}
            | Загальна сума            : ${summary.totalSum}
            +----------------------------------------+
            """.trimIndent()
        }
        .also { reportText ->
            println(reportText)
        }
}
