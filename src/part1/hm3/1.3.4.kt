package hm3

fun countMatches(strings: Collection<String>, condition: (String) -> Boolean): Int {
    var totalMatched = 0
    strings.forEach { item ->
        if (condition(item)) {
            totalMatched++
        }
    }
    return totalMatched
}

fun main() {
    val dataset = listOf("module1", "kotlin", "sprint42", "core", "build7", "api")

    val hasNumericChar = countMatches(dataset) { text ->
        text.any { symbol -> symbol in '0'..'9' }
    }

    println("Колекція рядків: $dataset")
    println("Кількість елементів із цифрами: $hasNumericChar")
}
