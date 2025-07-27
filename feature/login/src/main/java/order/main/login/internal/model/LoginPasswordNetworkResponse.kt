package order.main.login.internal.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginPasswordNetworkResponse(
    val user: LoginPasswordNetworkResponseUser?,
    val shop: LoginPasswordNetworkResponseShop?,
)