package order.main.login.internal.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import order.main.foundation.BizException
import order.main.login.internal.model.LoginPasswordNetworkResponse
import order.main.login.internal.model.UserDataResponse
import order.main.network.jsonRequestBody
import order.main.user.data.UserAccount
import order.main.user.data.UserInfo
import org.koin.core.annotation.Factory

@Factory(binds = [LoginPhoneAndPasswordApiDataSource::class])
internal class LoginPhoneAndPasswordApiDataSource(val httpClient: HttpClient) {

    suspend fun loginPhoneAndPassword(
        phone: String,
        password: String
    ): Result<UserDataResponse> {
        return runCatching {
            val response = httpClient.post(
                urlString = "",
            ) {
                jsonRequestBody {
                    this["phone"] = phone
                    this["password"] = password
                }
            }
            val (user, shop) = response.body<LoginPasswordNetworkResponse>()
            if (user == null || shop == null) {
                return Result.failure(
                    exception = BizException(code = -1, message = "不合法的服务返回，请联系开发者.")
                )
            }

            val userId = user.id
            val isActive = user.isActive

            if (userId == null || isActive == null) {
                return Result.failure(
                    exception = BizException(
                        code = -2,
                        message = "不合法的参数返回: userId: ${userId}, isActive: ${isActive}，请联系开发者."
                    )
                )
            }

            val userAccount = UserAccount(
                userId = userId,
                isActive = isActive
            )
            val userInfo = UserInfo(
                userId = userId,
                nickname = user.name ?: "",
                phone = user.phone ?: "",
                language = user.language
            )

            UserDataResponse(userAccount = userAccount, userInfo = userInfo)
        }
    }
}