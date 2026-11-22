package hm4.ex2

class Employee(
    val firstName: String,
    val lastName: String,
    val position: String
)

fun main() {
    println("--- Етап 1: Дослідження звичайного класу Employee ---")

    val workerA = Employee("Віктор", "Романенко", "DevOps Engineer")
    val workerB = Employee("Віктор", "Романенко", "DevOps Engineer")
    val workerAlias = workerA

    println("Вивід об'єкта workerA у консоль: $workerA")

    println("\nПорівняння двох окремих екземплярів (workerA і workerB):")
    println("  За посиланням (workerA === workerB) : ${workerA === workerB}")
    println("  За вмістом    (workerA == workerB)  : ${workerA == workerB}")

    println("\nПорівняння за спільним посиланням (workerA і workerAlias):")
    println("  За посиланням (workerA === workerAlias) : ${workerA === workerAlias}")
    println("  За вмістом    (workerA == workerAlias)  : ${workerA == workerAlias}")
}
