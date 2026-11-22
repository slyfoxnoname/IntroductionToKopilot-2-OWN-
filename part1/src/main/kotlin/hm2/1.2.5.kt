package hm2

import java.util.Scanner

// Задача e-olymp #8613: Умовний оператор - 5
fun main() {
    val reader = Scanner(System.`in`)
    val argX = reader.nextLong()

    val square = argX * argX
    val cube = square * argX

    val funcResult = when {
        argX >= 13L -> 3L * cube + 4L * square + 5L * argX + 6L
        else -> 3L * cube - 2L * square - 3L * argX - 4L
    }

    println(funcResult)
}
