package order.main.login.repositories

import order.main.login.ui.internal.datasource.LoginPhoneAndPasswordDataSource
import order.main.login.ui.internal.model.LoginPasswordResponse

class MyLoginRepository internal constructor(
    private val loginPhoneAndPasswordDataSource: LoginPhoneAndPasswordDataSource
) : LoginRepository {
    override suspend fun loginPassword(
        phone: String,
        password: String
    ): Result<LoginPasswordResponse> = loginPhoneAndPasswordDataSource.loginPhoneAndPassword(phone, password)
}