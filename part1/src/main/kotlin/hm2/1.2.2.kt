package hm2

import java.util.Locale

// Виведення дійсного числа із заданою точністю знаків після десяткової крапки
fun main() {
    val realValue = 2.7182818284
    val digitsAfterDot = 4

    val pattern = "%.${digitsAfterDot}f"
    val outputText = pattern.format(Locale.US, realValue)
    println(outputText)
}
