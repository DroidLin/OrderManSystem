package order.main.network

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import order.main.datastore.AbstractDataStore
import order.main.foundation.DataStoreNames
import order.main.network.internal.CookiePreference

class CookieDataStore(
    context: Context,
    coroutineScope: CoroutineScope
) : AbstractDataStore<CookiePreference>(
    context = context,
    initValue = CookiePreference(emptyList()),
    fileName = DataStoreNames.FILE_APP_COOKIE_CONTAINER,
    coroutineScope = coroutineScope
)