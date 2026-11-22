package hm2

import java.util.Scanner

// Обчислення добутку двох чисел через послідовне додавання
fun main() {
    val inputScanner = Scanner(System.`in`)
    val firstFactor = inputScanner.nextLong()
    val secondFactor = inputScanner.nextLong()

    var totalSum = 0L
    var counter = 0L
    while (counter < secondFactor) {
        totalSum += firstFactor
        counter++
    }

    println(totalSum)
}
