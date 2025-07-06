package order.main.user.tests

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import order.main.user.UserAccountLocalRepository
import order.main.user.data.UserAccount

internal class TestMyLocalUserAccountRepository : UserAccountLocalRepository {

    override val userAccount: Flow<UserAccount> = flowOf(UserAccount.Empty)

    override suspend fun updateUserData(userAccount: UserAccount) {}

    override suspend fun clear() {}
}
