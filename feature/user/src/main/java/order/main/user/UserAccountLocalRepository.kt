package order.main.user

import kotlinx.coroutines.flow.Flow
import order.main.user.data.UserAccount

interface UserAccountLocalRepository {

    val userAccount: Flow<UserAccount>

    suspend fun updateUserAccount(userAccount: UserAccount)

    suspend fun clear()
}
