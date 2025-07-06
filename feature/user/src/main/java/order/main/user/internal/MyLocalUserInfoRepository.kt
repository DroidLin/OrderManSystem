package order.main.user.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import order.main.user.UserInfoLocalRepository
import order.main.user.data.UserInfo

internal class MyLocalUserInfoRepository(
    val dataStore: LocalUserInfoDataStore
) : UserInfoLocalRepository {

    override val userAccount: Flow<UserInfo> = this.dataStore.data.map { it.toUserInfo() }

    override suspend fun updateUserData(userInfo: UserInfo) {
        dataStore.updateData { userInfo.toPreference() }
    }

    override suspend fun clear() {
        dataStore.updateData { PreferenceUserInfo.Empty }
    }
}