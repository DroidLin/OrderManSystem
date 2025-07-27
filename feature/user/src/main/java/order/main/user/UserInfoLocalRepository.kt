package order.main.user

import kotlinx.coroutines.flow.Flow
import order.main.user.data.UserInfo

interface UserInfoLocalRepository {

    val userInfo: Flow<UserInfo>

    suspend fun updateUserInfo(userInfo: UserInfo)

    suspend fun clear()
}