package order.main.network.internal

import kotlinx.serialization.Serializable

@Serializable
data class Cookie(
    val name: String,
    val value: String,
    val secure: Boolean,
    val httpOnly: Boolean,
    val maxAge: Int?,
    val expires: Long?,
    val domain: String?,
    val path: String?,
)
