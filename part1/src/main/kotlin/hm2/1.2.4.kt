package hm2

import kotlin.math.sqrt

// Клас для представлення комплексного числа a + bi
data class Complex(val rePart: Double, val imPart: Double)

fun myAbs(x: Int): Int = when {
    x < 0 -> -x
    else -> x
}

fun myAbs(x: Double): Double = when {
    x < 0.0 -> -x
    else -> x
}

fun myAbs(z: Complex): Double = sqrt(z.rePart * z.rePart + z.imPart * z.imPart)

fun main() {
    val sampleInt = -256
    val sampleDouble = -14.75
    val sampleComplex = Complex(8.0, 15.0)

    println("Abs(Int)     = ${myAbs(sampleInt)}")
    println("Abs(Double)  = ${myAbs(sampleDouble)}")
    println("Abs(Complex) = ${myAbs(sampleComplex)}")
}
