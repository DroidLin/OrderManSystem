package order.main.user.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import order.main.user.UserDataLocalRepository
import order.main.user.data.UserData

internal class MyLocalUserDataRepository(
    private val dataStore: LocalUserDataStore
) : UserDataLocalRepository {

    override val userData: Flow<UserData> = dataStore.data
        .map { it.toUserData() }

    override suspend fun updateUserData(userData: UserData) {
        dataStore.updateData {
            it.copy(
                id = userData.id
            )
        }
    }

    override suspend fun clear() {
        dataStore.updateData {
            PreferenceUserData(
                id = 0
            )
        }
    }

}