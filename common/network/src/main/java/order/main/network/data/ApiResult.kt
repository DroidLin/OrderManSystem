package order.main.network.data

import kotlinx.serialization.Serializable

@Serializable
data class ApiResult<T>(
    val code: Int?,
    val message: String?,
    val data: T?
)
