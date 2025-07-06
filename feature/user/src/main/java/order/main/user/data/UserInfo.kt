package order.main.user.data

data class UserInfo(
    val userId: Long,
    val nickname: String,
    val phone: String,
    val language: String?
) {

    companion object {

        val Empty = UserInfo(
            userId = 0,
            nickname = "",
            phone = "",
            language = null
        )
    }
}
