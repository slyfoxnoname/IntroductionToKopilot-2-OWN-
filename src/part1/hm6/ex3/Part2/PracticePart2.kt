package hm6.ex3.Part2

import kotlin.random.Random

fun generateRandomArray(size: Int, maxValue: Int): IntArray? {
    return if (size > 0 && maxValue > 0) {
        IntArray(size) { Random.nextInt(0, maxValue + 1) }
    } else {
        null
    }
}

fun main() {
    generateRandomArray(size = 10, maxValue = 50)
        ?.let { nums ->
            nums.apply {
                for (pos in indices) {
                    val v = this[pos]
                    this[pos] = if (v % 2 == 0) v / 2 else v * 2
                }
            }
        }
        ?.also { transformed ->
            println("Сформований масив після змін: ${transformed.contentToString()}")
        }
        ?.run {
            this.maxOrNull()
        }
        ?.run {
            println("Максимальний елемент у масиві: $this")
        }
        ?: println("Помилка вхідних даних")
}
