package order.main.datastore

import org.koin.core.annotation.Module

@Module(
    includes = [
        SerializerModule::class
    ]
)
class DataStoreModule
