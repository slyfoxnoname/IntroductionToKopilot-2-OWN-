package hm6.ex2

data class AccountUser(val username: String, val isActive: Boolean)

fun getActiveUsernames(users: List<AccountUser>): List<String> {
    return users.mapNotNull { account ->
        account.takeIf { it.isActive }?.username
    }
}

fun main() {
    val userDirectory = listOf(
        AccountUser("alice123", true),
        AccountUser("bob_the_builder", false),
        AccountUser("charlie99", true)
    )

    println(getActiveUsernames(userDirectory))
}
