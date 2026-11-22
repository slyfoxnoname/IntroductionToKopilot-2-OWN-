package hm6.ex2

data class UserProfile(val name: String?)

fun getNotificationPreferences(user: Any, emailEnabled: Boolean, smsEnabled: Boolean): List<String> {
    val profile = user as? UserProfile ?: return emptyList()
    val recipientName = profile.name ?: "Guest"

    val emailSetting = "Email Notifications enabled for $recipientName".takeIf { emailEnabled }
    val smsSetting = "SMS Notifications enabled for $recipientName".takeIf { smsEnabled }

    return listOfNotNull(emailSetting, smsSetting)
}

fun main() {
    val firstUser = UserProfile("Alice")
    val guestUser = UserProfile(null)
    val wrongTypeObj = "NotAUser"

    println(getNotificationPreferences(firstUser, emailEnabled = true, smsEnabled = false))
    println(getNotificationPreferences(guestUser, emailEnabled = false, smsEnabled = true))
    println(getNotificationPreferences(wrongTypeObj, emailEnabled = true, smsEnabled = true))
}
