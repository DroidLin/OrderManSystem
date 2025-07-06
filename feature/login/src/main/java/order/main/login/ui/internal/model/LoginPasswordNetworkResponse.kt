package order.main.login.ui.internal.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginPasswordNetworkResponse(
    val user: LoginPasswordNetworkResponseUser?,
    val shop: LoginPasswordNetworkResponseShop?,
)