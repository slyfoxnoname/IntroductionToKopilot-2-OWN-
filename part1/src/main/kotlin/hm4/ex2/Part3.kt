package hm4.ex2

fun main() {
    println("--- Етап 3: Перевірка класу ManualEmployee ---")

    val manualOne = ManualEmployee("Віктор", "Романенко", "DevOps Engineer")
    val manualTwo = ManualEmployee("Віктор", "Романенко", "DevOps Engineer")

    println("Об'єкт manualOne: $manualOne")
    println("Структурне порівняння (manualOne == manualTwo) : ${manualOne == manualTwo}")
    println("Порівняння посилань   (manualOne === manualTwo): ${manualOne === manualTwo}")
    println("Рівність хеш-кодів                             : ${manualOne.hashCode() == manualTwo.hashCode()}")

    val (fName, lName, currentRole) = manualOne
    println("Результат деструктуризації: $fName $lName [$currentRole]")

    val nextLevel = manualOne.copy(position = "Head of Infrastructure")
    println("Створено копію зі зміненою посадою: $nextLevel")

    val analystA = ManualEmployee("Наталія", "Лисенко", "Data Analyst").apply { bonus = 500 }
    val analystB = ManualEmployee("Наталія", "Лисенко", "Data Analyst").apply { bonus = 3500 }
    println("Порівняння при відмінних преміях (analystA == analystB): ${analystA == analystB}")
}
