package order.main.user.internal

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import order.main.datastore.AbstractDataStore
import order.main.foundation.DataStoreNames

internal class LocalUserDataStore(
    context: Context,
    coroutineScope: CoroutineScope
) : AbstractDataStore<PreferenceUserData>(
    context = context,
    initValue = PreferenceUserData.Empty,
    fileName = DataStoreNames.FILE_USER_DATA_STORE,
    coroutineScope = coroutineScope
)