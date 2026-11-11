package hm4.ex1

import kotlin.math.sqrt

sealed interface Roots {
    data class TwoRoots(val x1: Double, val x2: Double) : Roots
    data class OneRoot(val x: Double) : Roots
    data object NoRealRoots : Roots
}

class QuadraticEquation(
    coeffA: Double,
    var b: Double,
    var c: Double
) {
    var a: Double = coeffA
        set(updatedA) {
            if (updatedA == 0.0) {
                throw IllegalArgumentException("Коефіцієнт при x^2 (a) не повинен дорівнювати нулю!")
            }
            field = updatedA
        }

    init {
        if (coeffA == 0.0) {
            throw IllegalArgumentException("Коефіцієнт при x^2 (a) не повинен дорівнювати нулю!")
        }
    }

    constructor(a: Int, b: Int, c: Int) : this(
        coeffA = a.toDouble(),
        b = b.toDouble(),
        c = c.toDouble()
    )

    constructor(b: Double, c: Double) : this(
        coeffA = 1.0,
        b = b,
        c = c
    )

    val discriminant: Double
        get() = b * b - 4.0 * a * c

    fun solve(): List<Double> {
        val disc = discriminant
        val denominator = 2.0 * a

        return when {
            disc > 0.0 -> {
                val delta = sqrt(disc)
                val root1 = (-b + delta) / denominator
                val root2 = (-b - delta) / denominator
                listOf(root1, root2)
            }
            disc == 0.0 -> listOf(-b / denominator)
            else -> emptyList()
        }
    }

    fun solveWithRoots(): Roots {
        val solutions = solve()
        return when (solutions.size) {
            2 -> Roots.TwoRoots(solutions[0], solutions[1])
            1 -> Roots.OneRoot(solutions[0])
            else -> Roots.NoRealRoots
        }
    }

    override fun toString(): String = "${a}x^2 + (${b})x + (${c}) = 0"
}
