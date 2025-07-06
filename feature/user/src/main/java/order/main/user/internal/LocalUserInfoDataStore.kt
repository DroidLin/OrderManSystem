package order.main.user.internal

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import order.main.datastore.AbstractDataStore

internal class LocalUserInfoDataStore(
    context: Context,
    coroutineScope: CoroutineScope
) : AbstractDataStore<PreferenceUserInfo>(
    context = context,
    initValue = PreferenceUserInfo.Empty,
    fileName = "local_user_preference_info.pb",
    coroutineScope = coroutineScope
)