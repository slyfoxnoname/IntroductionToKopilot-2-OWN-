package hm4.ex3

import java.util.Locale

fun main() {
    println(">>> 1. Відбір, сортування за рейтингом та форматування <<<")
    val topAffordableInStock = products
        .filter { prod -> prod.inStock && prod.rating >= 4.7 && prod.price < 1000.0 }
        .sortedByDescending { prod -> prod.rating }
        .map { prod -> "Назва: ${prod.name} | Оцінка: ${prod.rating} | Вартість: $${prod.price}" }

    topAffordableInStock.forEach { line -> println(line) }

    println("\n>>> 2. Пошук елементів та перевірка предикатів (find, any, all) <<<")
    val costlyLaptopTitle = products
        .firstOrNull { prod -> prod.category == "Laptops" && prod.price > 1200.0 }
        ?.name ?: "Відсутній"
    println("Ноутбук із ціною понад $1200: $costlyLaptopTitle")

    val audioAbove300Exists = products.any { prod -> prod.category == "Audio" && prod.price > 300.0 }
    println("Наявність аудіопристроїв дорожчих за $300: $audioAbove300Exists")

    val phonesAllGoodRating = products
        .filter { prod -> prod.category == "Smartphones" }
        .all { prod -> prod.rating > 4.0 }
    println("Усі смартфони мають оцінку > 4.0: $phonesAllGoodRating")

    println("\n>>> 3. Розподіл за наявністю та групування за категоріями <<<")
    val (inStockList, outOfStockList) = products.partition { prod -> prod.inStock }
    println("Доступно для замовлення : ${inStockList.size}")
    println("Немає на складі         : ${outOfStockList.size}")

    println("\nЛідери за ціною в розрізі категорій:")
    val groupedCatalog = products.groupBy { prod -> prod.category }
    for ((catName, itemsInCat) in groupedCatalog) {
        val highestPriced = itemsInCat.maxByOrNull { prod -> prod.price }
        if (highestPriced != null) {
            println("  [$catName] Найдорожча позиція: ${highestPriced.name} — $${highestPriced.price}")
        }
    }

    println("\n>>> 4. Використання власної функції filterAndTransform <<<")
    val budgetOffers = products.filterAndTransform(
        predicate = { prod -> prod.price < 300.0 }
    ) { prod ->
        "Вигідна пропозиція: ${prod.name} за ціною $${prod.price}!"
    }
    budgetOffers.forEach { offer -> println(offer) }

    println("\n>>> Додатково: Обчислення сумарної та середньої ціни на складі через fold <<<")
    val sumInStock = inStockList.fold(0.0) { runningTotal, prod -> runningTotal + prod.price }
    val avgInStock = if (inStockList.isNotEmpty()) sumInStock / inStockList.size else 0.0

    println("Загальна вартість наявних позицій : $$sumInStock")
    println("Середній чек по наявних товарах   : $${"%.2f".format(Locale.US, avgInStock)}")
}
