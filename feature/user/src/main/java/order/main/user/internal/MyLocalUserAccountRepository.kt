package order.main.user.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import order.main.user.UserAccountLocalRepository
import order.main.user.data.UserAccount

internal class MyLocalUserAccountRepository(
    private val dataStore: LocalUserDataStore
) : UserAccountLocalRepository {

    override val userAccount: Flow<UserAccount> = dataStore.data
        .map { it.toUserData() }

    override suspend fun updateUserData(userAccount: UserAccount) {
        dataStore.updateData {
            it.copy(
                userId = userAccount.userId,
            )
        }
    }

    override suspend fun clear() {
        dataStore.updateData { PreferenceUserData.Empty }
    }

}