package hm2

// Перевірка року на високосність (e-olymp #5054)
fun main() {
    val rawLine = readlnOrNull() ?: return
    val y = rawLine.trim().toInt()

    val verdict = when {
        y % 400 == 0 -> "YES"
        y % 100 == 0 -> "NO"
        y % 4 == 0 -> "YES"
        else -> "NO"
    }
    println(verdict)
}
