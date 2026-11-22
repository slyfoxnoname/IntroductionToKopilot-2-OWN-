package hm6.ex1

data class Employee(val name: String, var salary: Int)

fun employeeById(id: Int): Employee? = when (id) {
    1 -> Employee("Mary", 20)
    2 -> null
    3 -> Employee("John", 21)
    4 -> Employee("Ann", 23)
    else -> null
}

fun salaryById(id: Int): Int {
    val foundEmployee = employeeById(id)
    return foundEmployee?.salary ?: 0
}

fun main() {
    val salarySum = (1..5).sumOf(::salaryById)
    println(salarySum)
}
