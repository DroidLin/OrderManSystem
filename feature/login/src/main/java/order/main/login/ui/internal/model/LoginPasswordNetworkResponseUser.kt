package order.main.login.ui.internal.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginPasswordNetworkResponseUser(
    val id: Long?,
    val phone: String?,
    val name: String?,
    val language: String?,
    val isActive: Boolean?,
    val shopId: Int?,
    val roles: List<String>?
)
