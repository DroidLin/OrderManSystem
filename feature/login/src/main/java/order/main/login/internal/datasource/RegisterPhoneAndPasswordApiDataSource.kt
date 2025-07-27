package order.main.login.internal.datasource

import io.ktor.client.HttpClient
import order.main.login.internal.model.UserDataResponse
import org.koin.core.annotation.Factory

@Factory(binds = [RegisterPhoneAndPasswordApiDataSource::class])
internal class RegisterPhoneAndPasswordApiDataSource(private val httpClient: HttpClient) {

    suspend fun doRegister(

    ): Result<UserDataResponse> {
        return Result.failure(Throwable())
    }
}