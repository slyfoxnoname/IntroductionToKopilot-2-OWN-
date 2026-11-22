package hm5.ex1

data class User(val id: Int, var email: String)

fun updateEmail(user: User, newEmail: String): User =
    user.apply {
        email = newEmail
    }.also { targetUser ->
        println("Updating email for user with ID: ${targetUser.id}")
    }

fun main() {
    val initialUser = User(1, "old_email@example.com")
    val resultUser = updateEmail(initialUser, "new_email@example.com")

    println("Updated User: $resultUser")
}
