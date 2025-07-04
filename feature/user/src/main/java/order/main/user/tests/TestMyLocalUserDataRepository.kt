package order.main.user.tests

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import order.main.user.UserDataLocalRepository
import order.main.user.data.UserData

internal class TestMyLocalUserDataRepository : UserDataLocalRepository {

    override val userData: Flow<UserData> = flowOf(UserData(0L))

    override suspend fun updateUserData(userData: UserData) {}

    override suspend fun clear() {}
}