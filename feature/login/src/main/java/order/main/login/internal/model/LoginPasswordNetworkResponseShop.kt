package order.main.login.internal.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginPasswordNetworkResponseShop(
    val id: Long?,
    val shopName: String?,
    val address: String?,
    val phone: String?,
    val status: String?,
    val authorizationStart: Long?,
    val authorizationEnd: Long?,
    val createdAt: Long?,
    val updatedAt: Long?,
    val createdBy: String?,
    val updatedBy: String?,
)