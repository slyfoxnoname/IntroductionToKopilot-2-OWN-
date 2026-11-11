package hm7.Part2

// Контракти для повернення коштів та надсилання електронних квитанцій
interface Refundable {
    fun refund(amount: Double): Boolean
}

interface EReceiptable {
    val receiptEmail: String

    fun sendReceipt() {
        println("  [Квитанція] Електронний чек відправлено на e-mail: $receiptEmail")
    }
}

// Абстрактна основа для всіх платіжних операцій
abstract class PaymentMethod(
    val transactionId: String,
    val amount: Double,
    val currency: String = "UAH"
) {
    init {
        if (amount <= 0.0) {
            throw IllegalArgumentException("Некоректна сума операції ($amount): значення має бути додатним")
        }
    }

    abstract fun processPayment(): Boolean

    open fun printDetails() {
        println("Операція №$transactionId | Сума: $amount $currency")
    }
}

// Оплата банківською карткою
class CreditCardPayment(
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val cardNumber: String,
    override val receiptEmail: String
) : PaymentMethod(transactionId, amount, currency), Refundable, EReceiptable {

    private fun lastFourDigits(): String = cardNumber.takeLast(4)

    override fun processPayment(): Boolean {
        println("  [Картка] Списано $amount $currency з картки *${lastFourDigits()}. Статус: OK.")
        return true
    }

    override fun refund(amount: Double): Boolean {
        println("  [Повернення] Зараховано $amount $currency назад на картку *${lastFourDigits()}.")
        return true
    }

    override fun printDetails() {
        super.printDetails()
        println("  Тип: Банківська картка (номер: **** ${lastFourDigits()})")
    }
}

// Оплата криптовалютою
class CryptoPayment(
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val cryptoAddress: String,
    val networkFee: Double
) : PaymentMethod(transactionId, amount, currency), Refundable {

    private fun formatAddress(): String =
        "${cryptoAddress.take(6)}...${cryptoAddress.takeLast(4)}"

    override fun processPayment(): Boolean {
        println("  [Крипто] Переказано $amount $currency (+ мережевий збір $networkFee) на адресу ${formatAddress()}.")
        return true
    }

    override fun refund(amount: Double): Boolean {
        return if (amount > networkFee) {
            val payout = amount - networkFee
            println("  [Повернення] Відправлено $payout $currency на криптогаманець ${formatAddress()} (вирахувано комісію $networkFee).")
            true
        } else {
            println("  [Помилка] Повернення неможливе: сума $amount $currency не перевищує комісію мережі $networkFee.")
            false
        }
    }

    override fun printDetails() {
        super.printDetails()
        println("  Тип: Криптовалюта (гаманець: ${ cryptoAddress.take(6) }...)")
    }
}

// Оплата при отриманні (післяплата)
class CashOnDeliveryPayment(
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val deliveryAddress: String,
    override val receiptEmail: String
) : PaymentMethod(transactionId, amount, currency), EReceiptable {

    override fun processPayment(): Boolean {
        println("  [Післяплата] Очікується оплата $amount $currency готівкою/терміналом за адресою: $deliveryAddress.")
        return true
    }

    override fun printDetails() {
        super.printDetails()
        println("  Тип: Оплата при отриманні (точка видачі: $deliveryAddress)")
    }

    override fun sendReceipt() {
        println("  [Повідомлення] Дублікат чека надіслано у SMS та на пошту $receiptEmail")
    }
}

fun processMassRefund(items: List<PaymentMethod>, refundPercentage: Double) {
    var completedRefunds = 0

    items.forEach { method ->
        if (method is Refundable) {
            val sumToReturn = method.amount * (refundPercentage / 100.0)
            val isSuccess = method.refund(sumToReturn)
            if (isSuccess) {
                completedRefunds++
            }
        }
    }

    println("Результат масового повернення: виконано $completedRefunds із ${items.size} операцій.")
}

fun main() {
    val paymentQueue: List<PaymentMethod> = listOf(
        CreditCardPayment(
            transactionId = "PAY-501",
            amount = 4200.0,
            cardNumber = "4441114455667890",
            receiptEmail = "olga.petrenko@ukr.net"
        ),
        CryptoPayment(
            transactionId = "PAY-502",
            amount = 18000.0,
            cryptoAddress = "0x88F1A900BC34E71109D2B54F",
            networkFee = 60.0
        ),
        CashOnDeliveryPayment(
            transactionId = "PAY-503",
            amount = 1650.0,
            deliveryAddress = "м. Одеса, Відділення №12",
            receiptEmail = "ivan.moroz@example.com"
        )
    )

    println("**************************************************")
    println("         ВИКОНАННЯ ЧЕРГИ ПЛАТЕЖІВ")
    println("**************************************************")
    for (operation in paymentQueue) {
        operation.printDetails()
        operation.processPayment()
        if (operation is EReceiptable) {
            operation.sendReceipt()
        }
        println("--------------------------------------------------")
    }

    println()
    println("**************************************************")
    println("      ПОВЕРНЕННЯ 50% КОШТІВ ЗА ТРАНЗАКЦІЯМИ")
    println("**************************************************")
    processMassRefund(paymentQueue, 50.0)
}
