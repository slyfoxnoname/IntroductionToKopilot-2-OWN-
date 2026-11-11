package hm6.ex2

fun validateStock(requested: Int?, available: Int?): Int {
    val needed = requested ?: return -1
    val inWarehouse = available ?: return -1

    if (needed < 0 || needed > inWarehouse) {
        return -1
    }
    return needed
}

fun main() {
    println(validateStock(5, 10))
    println(validateStock(null, 10))
    println(validateStock(-2, 10))
}
