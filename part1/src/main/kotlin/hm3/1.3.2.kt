package hm3

fun repeatN(count: Int, task: () -> Unit) {
    var step = 0
    while (step < count) {
        task()
        step++
    }
}

fun main() {
    val paths = listOf("title", "year", "author")
    val domainPrefix = "https://example.com/book-info"
    val recordId = 5

    val fullLinks = paths.map { segment -> "$domainPrefix/$recordId/$segment" }
    println("Список сформованих URL: $fullLinks")

    println("Виклик функції repeatN:")
    repeatN(5) {
        println("Hello")
    }
}
