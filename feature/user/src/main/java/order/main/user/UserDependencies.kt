package order.main.user

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import order.main.foundation.AndroidContext
import order.main.foundation.AppLifecycleCoroutineScope
import order.main.user.internal.LocalUserDataStore
import order.main.user.internal.LocalUserInfoDataStore
import order.main.user.internal.MyLocalUserAccountRepository
import order.main.user.internal.MyLocalUserInfoRepository
import order.main.user.qualifier.MyUserDataRepository
import order.main.user.qualifier.MyUserInfoRepository
import order.main.user.tests.TestMyLocalUserAccountRepository
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

    @MyUserDataRepository
    @Factory(binds = [UserAccountLocalRepository::class, MyLocalUserAccountRepository::class])
    internal fun myUserLocalRepository(
        dataStore: LocalUserDataStore
    ): UserAccountLocalRepository = MyLocalUserAccountRepository(dataStore)

    @TestOnly
    @TestUserDataRepository
    @Factory(binds = [UserAccountLocalRepository::class, TestMyLocalUserAccountRepository::class])
    internal fun testUserLocalRepository(
    ): TestMyLocalUserAccountRepository = TestMyLocalUserAccountRepository()

    @Factory(binds = [UserAccountLocalRepository::class, MyLocalUserAccountRepository::class])
    internal fun userLocalRepository(
        @MyUserDataRepository
        repository: UserAccountLocalRepository
    ): UserAccountLocalRepository = repository

    @Singleton
    internal fun localUserInfoDataStore(
        @AndroidContext
        context: Context,
        @AppLifecycleCoroutineScope
        coroutineScope: CoroutineScope,
    ): LocalUserInfoDataStore = LocalUserInfoDataStore(context, coroutineScope)

    @MyUserInfoRepository
    @Factory(binds = [UserInfoLocalRepository::class, MyLocalUserAccountRepository::class])
    internal fun userInfoLocalRepository(
        dataStore: LocalUserInfoDataStore
    ): UserInfoLocalRepository = MyLocalUserInfoRepository(dataStore)

    @Factory(binds = [UserInfoLocalRepository::class, MyLocalUserAccountRepository::class])
    internal fun userInfoLocalRepository(
        @MyUserInfoRepository
        repository: UserInfoLocalRepository
    ): UserInfoLocalRepository = repository

}