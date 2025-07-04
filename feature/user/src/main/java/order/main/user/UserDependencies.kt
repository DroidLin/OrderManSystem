package order.main.user

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import order.main.foundation.AndroidContext
import order.main.foundation.AppLifecycleCoroutineScope
import order.main.user.internal.LocalUserDataStore
import order.main.user.internal.MyLocalUserDataRepository
import order.main.user.tests.TestMyLocalUserDataRepository
import order.main.user.tests.TestUserDataRepository
import org.jetbrains.annotations.TestOnly
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class UserDependencies {

    @Singleton
    internal fun localUserDataStore(
        @AndroidContext
        context: Context,
        @AppLifecycleCoroutineScope
        coroutineScope: CoroutineScope
    ): LocalUserDataStore = LocalUserDataStore(context, coroutineScope)

    @Factory(binds = [UserDataLocalRepository::class, MyLocalUserDataRepository::class])
    internal fun userLocalRepository(
        dataStore: LocalUserDataStore
    ): UserDataLocalRepository = MyLocalUserDataRepository(dataStore)

    @MyUserDataRepository
    @Factory(binds = [UserDataLocalRepository::class, MyLocalUserDataRepository::class])
    internal fun myUserLocalRepository(
        dataStore: LocalUserDataStore
    ): UserDataLocalRepository = MyLocalUserDataRepository(dataStore)

    @TestOnly
    @TestUserDataRepository
    @Factory(binds = [UserDataLocalRepository::class, TestMyLocalUserDataRepository::class])
    internal fun testUserLocalRepository(
    ): TestMyLocalUserDataRepository = TestMyLocalUserDataRepository()
}