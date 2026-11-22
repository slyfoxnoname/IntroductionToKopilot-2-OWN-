package hm6.ex2

data class Subscription(val name: String, val isActive: Boolean)

fun getActiveSubscription(subscriptions: List<Subscription>): Subscription? {
    return subscriptions.singleOrNull { item -> item.isActive }
}

fun main() {
    val validPlanList = listOf(
        Subscription("Basic Plan", false),
        Subscription("Premium Plan", true)
    )

    val conflictingPlanList = listOf(
        Subscription("Basic Plan", true),
        Subscription("Premium Plan", true)
    )

    println(getActiveSubscription(validPlanList))
    println(getActiveSubscription(conflictingPlanList))
}
