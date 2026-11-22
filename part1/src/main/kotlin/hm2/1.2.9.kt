package hm2

import java.math.BigInteger
import java.util.Scanner

// Задача e-olymp #271: Обчислення факторіала великого числа
fun main() {
    val inputScanner = Scanner(System.`in`)
    val limit = inputScanner.nextInt()

    val answer = (2..limit).fold(BigInteger.ONE) { acc, current ->
        acc.multiply(BigInteger.valueOf(current.toLong()))
    }

    println(answer)
}
