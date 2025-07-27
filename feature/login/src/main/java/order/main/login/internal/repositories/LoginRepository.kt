package order.main.login.internal.repositories

import order.main.login.internal.model.UserDataResponse

internal interface LoginRepository {

    suspend fun loginPassword(
        phone: String,
        password: String
    ): Result<UserDataResponse>

    suspend fun registerAccount(
        phone: String,
        password: String,
        nickname: String
    ): Result<UserDataResponse>
}
