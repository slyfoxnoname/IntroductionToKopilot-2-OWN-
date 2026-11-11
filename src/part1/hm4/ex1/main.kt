package hm4.ex1

import java.util.Locale
import kotlin.random.Random

fun main() {
    println(">>> Перевірка етапів 1-3: створення та модифікація рівнянь <<<")

    val eqOne = QuadraticEquation(2, -10, 12) // 2x^2 - 10x + 12 = 0 -> корені 3.0, 2.0
    val eqMonic = QuadraticEquation(-8.0, 16.0) // x^2 - 8x + 16 = 0 -> корінь 4.0

    println("Повне рівняння:   $eqOne | D = ${eqOne.discriminant} | Розв'язки: ${eqOne.solve()}")
    println("Зведене рівняння: $eqMonic | D = ${eqMonic.discriminant} | Розв'язки: ${eqMonic.solve()}")

    println("\n>>> Модифікація вільного члена та другого коефіцієнта <<<")
    eqOne.b = 4.0
    eqOne.c = 8.0
    println("Оновлене eqOne: $eqOne | D = ${eqOne.discriminant} | Розв'язки: ${eqOne.solve()}")

    println("\n>>> Спроба встановити некоректний коефіцієнт a = 0.0 <<<")
    try {
        eqOne.a = 0.0
    } catch (err: IllegalArgumentException) {
        println("Перехоплено виняток валідації: ${err.message}")
    }

    println("\n>>> Етап 4: Генерація 100 випадкових рівнянь [-50..50] <<<")
    val batch = List(100) {
        var coeffA = Random.nextInt(-50, 51)
        while (coeffA == 0) {
            coeffA = Random.nextInt(-50, 51)
        }
        val coeffB = Random.nextInt(-50, 51)
        val coeffC = Random.nextInt(-50, 51)
        QuadraticEquation(coeffA, coeffB, coeffC)
    }

    val withTwoSolutions = batch.filter { eq -> eq.discriminant > 0.0 }
    println("Кількість рівнянь, що мають два дійсні корені: ${withTwoSolutions.size} із ${batch.size}\n")

    withTwoSolutions.forEachIndexed { pos, equation ->
        when (val outcome = equation.solveWithRoots()) {
            is Roots.TwoRoots -> {
                val formattedLine = String.format(
                    Locale.US,
                    "[%02d] %s | D = %.2f => x1 = %.3f, x2 = %.3f",
                    pos + 1,
                    equation,
                    equation.discriminant,
                    outcome.x1,
                    outcome.x2
                )
                println(formattedLine)
            }
            is Roots.OneRoot -> {
                println(String.format(Locale.US, "[%02d] %s => x = %.3f", pos + 1, equation, outcome.x))
            }
            is Roots.NoRealRoots -> {
                println("[%02d] $equation => дійсних коренів немає".format(pos + 1))
            }
        }
    }
}
