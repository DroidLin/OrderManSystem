package order.main.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import kotlinx.coroutines.CoroutineScope

abstract class AbstractDataStore<T : Any>(
    context: Context,
    initValue: T,
    fileName: String,
    coroutineScope: CoroutineScope,
) : DataStore<T> by DataStoreFactory.create(
    serializer = AppSerializer(defaultValue = initValue),
    scope = coroutineScope,
    produceFile = { context.dataStoreFile(fileName = fileName) }
)