package hm7.Part1

interface Messenger {
    fun sendMessage(message: String)
    fun receiveMessage(): String
}

class BasicMessenger : Messenger {
    override fun sendMessage(message: String) {
        println("Sending message: $message")
    }

    override fun receiveMessage(): String {
        return "You've got a new message!"
    }
}

class SmartMessenger(
    private val underlyingMessenger: BasicMessenger
) : Messenger by underlyingMessenger {
    override fun sendMessage(message: String) {
        println("Sending a smart message: $message")
        underlyingMessenger.sendMessage("{smart} $message")
    }
}

fun main() {
    val standardMessenger = BasicMessenger()
    val enhancedMessenger = SmartMessenger(standardMessenger)

    standardMessenger.sendMessage("Hello!")
    println(enhancedMessenger.receiveMessage())
    enhancedMessenger.sendMessage("Hello from SmartMessenger!")
}
