package hm3

fun calcProductRepeat(first: Int, second: Int): Int {
    var acc = 0
    repeat(second) {
        acc += first
    }
    return acc
}

fun calcProductForEach(first: Int, second: Int): Int {
    var acc = 0
    (1..second).forEach {
        acc += first
    }
    return acc
}

fun main() {
    val numA = 12
    val numB = 5

    println("Добуток чисел $numA і $numB:")
    println("  [repeat]  -> ${calcProductRepeat(numA, numB)}")
    println("  [forEach] -> ${calcProductForEach(numA, numB)}")
}
