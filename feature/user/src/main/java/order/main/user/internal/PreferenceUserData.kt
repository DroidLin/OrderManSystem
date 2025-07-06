package order.main.user.internal

import kotlinx.serialization.Serializable

@Serializable
internal data class PreferenceUserData(
    val userId: Long,
    val accessToken: String,
) {

    companion object {

        val Empty = PreferenceUserData(
            userId = 0L,
            accessToken = "",
        )
    }
}
