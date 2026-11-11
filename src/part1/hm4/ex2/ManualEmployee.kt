package hm4.ex2

class ManualEmployee(
    val firstName: String,
    val lastName: String,
    val position: String
) {
    var bonus: Int = 0

    override fun toString(): String {
        return "ManualEmployee(firstName=$firstName, lastName=$lastName, position=$position)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val that = other as? ManualEmployee ?: return false
        return this.firstName == that.firstName &&
            this.lastName == that.lastName &&
            this.position == that.position
    }

    override fun hashCode(): Int {
        var code = firstName.hashCode()
        code = code * 31 + lastName.hashCode()
        code = code * 31 + position.hashCode()
        return code
    }

    operator fun component1(): String = firstName
    operator fun component2(): String = lastName
    operator fun component3(): String = position

    fun copy(
        firstName: String = this.firstName,
        lastName: String = this.lastName,
        position: String = this.position
    ): ManualEmployee {
        return ManualEmployee(firstName, lastName, position)
    }
}
