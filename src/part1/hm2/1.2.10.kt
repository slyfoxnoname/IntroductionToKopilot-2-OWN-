package hm2

import java.util.Scanner

// Задача e-olymp #8923: Запис цифр натурального числа у зворотному порядку
fun main() {
    val inputScanner = Scanner(System.`in`)
    val tokenStr = inputScanner.next()

    val flipped = tokenStr.reversed().trimStart('0')
    val finalOutput = flipped.ifEmpty { "0" }

    println(finalOutput)
}
