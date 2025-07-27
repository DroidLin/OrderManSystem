package order.main.login.internal.repositories

import order.main.login.internal.datasource.LoginPhoneAndPasswordApiDataSource
import order.main.login.internal.datasource.RegisterPhoneAndPasswordApiDataSource
import order.main.login.internal.model.UserDataResponse
import org.koin.core.annotation.Factory

@Factory(binds = [LoginRepository::class])
internal class MyLoginRepository(
    private val loginPhoneAndPasswordApiDataSource: LoginPhoneAndPasswordApiDataSource,
    private val registerPhoneAndPasswordApiDataSource: RegisterPhoneAndPasswordApiDataSource,
) : LoginRepository {
    override suspend fun loginPassword(
        phone: String,
        password: String
    ): Result<UserDataResponse> =
        this.loginPhoneAndPasswordApiDataSource.loginPhoneAndPassword(phone, password)

    override suspend fun registerAccount(
        phone: String,
        password: String,
        nickname: String
    ): Result<UserDataResponse> = this.registerPhoneAndPasswordApiDataSource.doRegister()
}