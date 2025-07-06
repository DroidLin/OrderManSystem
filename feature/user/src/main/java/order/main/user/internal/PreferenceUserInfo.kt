package order.main.user.internal

import kotlinx.serialization.Serializable

@Serializable
internal data class PreferenceUserInfo(
    val userId: Long,
    val nickname: String,
    val phone: String,
    val language: String?
) {
    companion object {

        val Empty = PreferenceUserInfo(
            userId = 0,
            nickname = "",
            phone = "",
            language = null
        )
    }
}
