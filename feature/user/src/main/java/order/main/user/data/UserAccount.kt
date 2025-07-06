package order.main.user.data

data class UserAccount(
    val userId: Long,
    val accessToken: String,
) {

    companion object {

        val Empty = UserAccount(
            userId = 0,
            accessToken = ""
        )
    }
}
