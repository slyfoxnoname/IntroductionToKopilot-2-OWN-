package hm3

fun stringFilter(strings: Collection<String>, condition: (String) -> Boolean): List<String> {
    val keptItems = ArrayList<String>()
    for (entry in strings) {
        if (condition(entry)) {
            keptItems.add(entry)
        }
    }
    return keptItems
}

fun main() {
    val languages = listOf("Kotlin", "Java", "JavaScript", "Rust", "Julia", "Swift")

    val startsWithJ = stringFilter(languages) { lang -> lang.startsWith("J") }
    println("Початковий перелік мов: $languages")
    println("Мови, що починаються на 'J': $startsWithJ")
}
