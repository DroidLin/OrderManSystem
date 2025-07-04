package order.main.user

import kotlinx.coroutines.flow.Flow
import order.main.user.data.UserData

interface UserDataLocalRepository {

    val userData: Flow<UserData>

    suspend fun updateUserData(userData: UserData)

    suspend fun clear()
}
