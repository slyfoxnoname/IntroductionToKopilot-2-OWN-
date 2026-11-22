package hm2

import java.util.Scanner

// Задача e-olymp #923: Визначення пори року за номером місяця
fun main() {
    val reader = Scanner(System.`in`)
    val monthIndex = reader.nextInt()

    val seasonTitle = when (monthIndex) {
        3, 4, 5 -> "Spring"
        6, 7, 8 -> "Summer"
        9, 10, 11 -> "Autumn"
        12, 1, 2 -> "Winter"
        else -> return
    }
    println(seasonTitle)
}
