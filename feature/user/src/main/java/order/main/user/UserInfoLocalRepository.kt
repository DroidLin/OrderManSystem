package order.main.user

import kotlinx.coroutines.flow.Flow
import order.main.user.data.UserInfo

interface UserInfoLocalRepository {

    val userAccount: Flow<UserInfo>

    suspend fun updateUserData(userInfo: UserInfo)

    suspend fun clear()
}