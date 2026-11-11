package hm4.ex2

data class EmployeeData(
    val firstName: String,
    val lastName: String,
    val position: String
) {
    var bonus: Int = 0
}

fun main() {
    println("--- Етап 2: Дослідження data class EmployeeData ---")

    val devOne = EmployeeData("Віктор", "Романенко", "DevOps Engineer")
    val devTwo = EmployeeData("Віктор", "Романенко", "DevOps Engineer")

    println("Рядкове представлення devOne: $devOne")
    println("Порівняння за значенням (devOne == devTwo)  : ${devOne == devTwo}")
    println("Порівняння за посиланням (devOne === devTwo): ${devOne === devTwo}")

    val (fName, lName, jobTitle) = devOne
    println("Розпаковані властивості: $fName $lName, посада — $jobTitle")

    val upgradedDev = devOne.copy(position = "Cloud Architect")
    println("Новий екземпляр після copy(): $upgradedDev")

    val specialist1 = EmployeeData("Наталія", "Лисенко", "Data Analyst").apply { bonus = 2000 }
    val specialist2 = EmployeeData("Наталія", "Лисенко", "Data Analyst").apply { bonus = 8000 }
    println("Чи рівні об'єкти з різним bonus (specialist1 == specialist2): ${specialist1 == specialist2}")
}
