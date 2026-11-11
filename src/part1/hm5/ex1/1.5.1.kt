package hm5.ex1

data class ProductInfo(val priceInDollars: Double?)

class Product {
    fun getProductInfo(): ProductInfo? = ProductInfo(100.0)
}

fun convertToEuros(dollars: Double): Double = dollars * 0.85

fun Product.getPriceInEuros(): Double? =
    this.getProductInfo()?.priceInDollars?.let(::convertToEuros)

fun main() {
    val sampleProduct = Product()
    val convertedAmount = sampleProduct.getPriceInEuros()

    when (convertedAmount) {
        null -> println("Price information is not available.")
        else -> println("Price in Euros: €$convertedAmount")
    }
}
