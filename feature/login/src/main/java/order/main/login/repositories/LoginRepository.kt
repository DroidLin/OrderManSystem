package order.main.login.repositories

import order.main.login.ui.internal.model.LoginPasswordResponse

interface LoginRepository {

    suspend fun loginPassword(phone: String, password: String): Result<LoginPasswordResponse>
}
