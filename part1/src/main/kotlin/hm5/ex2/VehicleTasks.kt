package hm5.ex2

data class Vehicle(
    var brand: String = "",
    var model: String = "",
    var year: Int = 0,
    var licensePlate: String = ""
)

fun main() {
    val transport = Vehicle().apply {
        brand = "Mazda"
        model = "CX-5"
        year = 2022
        licensePlate = "ВС5599НР"
    }.also { created ->
        println("[Інфо] Додано автомобіль до бази: $created")
    }

    println()

    with(transport) {
        println("--- Технічний паспорт авто ---")
        println("Виробник       : $brand")
        println("Модельний ряд  : $model")
        println("Рік випуску    : $year")
        println("Держ. номер    : $licensePlate")
    }
}
