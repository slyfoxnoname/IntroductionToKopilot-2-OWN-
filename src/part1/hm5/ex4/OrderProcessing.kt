package hm5.ex4

data class OrderItem(
    val name: String,
    val price: Double,
    val quantity: Int
)

class Order(val id: String) {
    var customerName: String = ""
    var deliveryAddress: String = ""
    var discountPercent: Int = 0
    val items: MutableList<OrderItem> = mutableListOf()

    fun addItem(item: OrderItem) {
        items.add(item)
    }
}

fun main() {
    // 1. Ініціалізація замовлення (apply) та логування події (also)
    val clientOrder = Order("ORD-2026-774").apply {
        customerName = "Дарина Шевчук"
        deliveryAddress = "вул. Соборна, 58, Вінниця"
        discountPercent = 12

        addItem(OrderItem("Монітор 27\"", 9500.0, 1))
        addItem(OrderItem("Підставка для ноутбука", 1100.0, 1))
        addItem(OrderItem("Кабель DisplayPort", 450.0, 2))
    }.also { ord ->
        println("[SYSTEM] Зареєстровано замовлення №${ord.id}, кількість позицій: ${ord.items.size}")
    }

    // 2. Обчислення суми до оплати з урахуванням знижки (run)
    val discountedTotal: Double = clientOrder.run {
        val baseCost = items.sumOf { line -> line.price * line.quantity }
        val multiplier = 1.0 - (discountPercent / 100.0)
        baseCost * multiplier
    }
    println("Підсумкова вартість зі знижкою: $discountedTotal грн")

    // 3. Генерація рядка транзакції для платіжного шлюзу (let)
    val gatewayPayload: String = discountedTotal.let { sumUah ->
        val totalKopiykas = (sumUah * 100).toLong()
        "INV-${clientOrder.id}-KOP-$totalKopiykas"
    }
    println("Код для платіжного шлюзу: $gatewayPayload")

    // 4. Формування та виведення деталізованого чека (with)
    with(clientOrder) {
        println("******************************************")
        println("ТОВАРНИЙ ЧЕК: $id")
        println("Замовник : $customerName")
        println("Доставка : $deliveryAddress")
        println("------------------------------------------")
        println("Придбані позиції:")
        for (entry in items) {
            val subtotal = entry.price * entry.quantity
            println(" - ${entry.name} x${entry.quantity} = $subtotal грн")
        }
        println("------------------------------------------")
        println("Надана знижка   : $discountPercent%")
        println("СУМА ДО СПЛАТИ  : $discountedTotal грн")
        println("******************************************")
    }
}
