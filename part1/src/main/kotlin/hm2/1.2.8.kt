package hm2

import java.util.Scanner

// Перевірка числа на простоту методом перебору дільників до sqrt(n)
private fun checkPrimality(target: Long): Boolean {
    if (target <= 1L) return false
    if (target == 2L) return true
    if (target % 2L == 0L) return false

    var factor = 3L
    while (factor <= target / factor) {
        if (target % factor == 0L) return false
        factor += 2L
    }
    return true
}

fun main() {
    val inputScanner = Scanner(System.`in`)
    val num = inputScanner.nextLong()
    println(if (checkPrimality(num)) "YES" else "NO")
}
