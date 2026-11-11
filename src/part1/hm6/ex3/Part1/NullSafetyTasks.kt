package hm6.ex3.Part1

data class Address(
    val street: String,
    val city: String?,
    val postalCode: String?
)

data class Client(
    val id: Int,
    val name: String,
    val email: String?,
    val address: Address?,
    val extraData: Any?
)

val registeredClients = listOf(
    Client(
        id = 1,
        name = "Катерина",
        email = "kateryna@ukr.net",
        address = Address("вул. Прорізна, 15", "Київ", "01004"),
        extraData = "Корпоративний замовник"
    ),
    Client(
        id = 2,
        name = "Роман",
        email = null,
        address = Address("вул. Франка, 31", "Івано-Франківськ", null),
        extraData = 777
    ),
    Client(
        id = 3,
        name = "Аліна",
        email = "alina.m@example.com",
        address = null,
        extraData = null
    ),
    Client(
        id = 4,
        name = "Ігор",
        email = null,
        address = Address("пр-т Миру, 20", null, null),
        extraData = "Зателефонувати за годину до приїзду"
    )
)

// Завдання 1: Складання поштового рядка доставки
fun getShippingLabel(client: Client): String {
    val deliveryAddr = client.address ?: return "Самовивіз: для клієнта ${client.name} адреса відсутня"
    val resolvedCity = deliveryAddr.city ?: "Місто не вказано"
    val resolvedZip = deliveryAddr.postalCode ?: "Індекс невідомий"
    return "${deliveryAddr.street}, $resolvedCity, $resolvedZip"
}

// Завдання 2: Безпечне читання примітки клієнта через as?
fun printClientNote(client: Client) {
    val noteContent = (client.extraData as? String) ?: "Додаткові примітки відсутні"
    println("[Клієнт #${client.id} ${client.name}] Примітка: $noteContent")
}

// Завдання 4: Перевірка наявності пошти або викидання IllegalArgumentException
fun getClientEmailOrThrow(client: Client): String {
    return client.email ?: throw IllegalArgumentException("Відсутній email для клієнта ID=${client.id} (${client.name})")
}

// Завдання 5: Використання оператора !! для отримання поштового індексу
fun forceGetPostalCode(client: Client): String {
    val nonNullAddress = client.address!!
    return nonNullAddress.postalCode!!
}

fun main() {
    println("--- Завдання 1: Формування адрес доставки ---")
    for (person in registeredClients) {
        println("${person.name} -> ${getShippingLabel(person)}")
    }

    println("\n--- Завдання 2: Перевірка додаткових даних (extraData) ---")
    registeredClients.forEach { person -> printClientNote(person) }

    println("\n--- Завдання 3: Фільтрація непорожніх електронних адрес ---")
    val mailingList = registeredClients.map { it.email }.filterNotNull()
    val minChars = mailingList.minOfOrNull { mail -> mail.length }
    println("Активні електронні адреси: $mailingList")
    println("Найменша довжина email:    $minChars")

    println("\n--- Завдання 4: Обробка відсутнього email через виняток ---")
    try {
        println(getClientEmailOrThrow(registeredClients[1]))
    } catch (error: IllegalArgumentException) {
        println("Спрацював захист: ${error.message}")
    }

    println("\n--- Завдання 5: Примусовий доступ через оператор !! ---")
    println("Поштовий індекс (${registeredClients[0].name}): ${forceGetPostalCode(registeredClients[0])}")
    try {
        println(forceGetPostalCode(registeredClients[1]))
    } catch (error: NullPointerException) {
        println("Перехоплено NullPointerException при зверненні до null-поля через !!")
    }
}
