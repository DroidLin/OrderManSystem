package order.main.network.internal

import kotlinx.serialization.Serializable

@Serializable
data class CookiePreference(
    val cookies: List<Cookie>
)
