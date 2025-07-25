package order.main.user

import kotlinx.coroutines.flow.Flow
import order.main.user.data.UserAccount

interface UserAccountLocalRepository {

    val userAccount: Flow<UserAccount>

    suspend fun updateUserData(userAccount: UserAccount)

    suspend fun clear()
}
