package order.main.user.data

data class UserAccount(
    val userId: Long,
    val isActive: Boolean? = null,
) {

    companion object {

        val Empty = UserAccount(
            userId = 0,
            isActive = null
        )
    }
}
