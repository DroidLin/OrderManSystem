package order.main.user

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import order.main.user.data.UserAccount
import order.main.user.qualifier.UserScopedCoroutineScope
import order.main.user.qualifier.UserScopedData
import org.koin.core.annotation.Module
import org.koin.core.annotation.Property
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

/**
 * 需提供[AppUserScope]作用域
 */
@Module
class UserScopeDependencies {

    @UserScopedData
    @Scope(value = AppUserScope::class)
    @Scoped(binds = [UserAccount::class])
    fun userScopedData(
        @Property(KEY_PROPERTY_USER_DATA)
        userAccount: UserAccount
    ): UserAccount = userAccount

    @Scope(value = AppUserScope::class)
    @Scoped(binds = [UserAccount::class])
    fun userData(@UserScopedData userAccount: UserAccount): UserAccount = userAccount

    @UserScopedCoroutineScope
    @Scope(value = AppUserScope::class)
    @Scoped(binds = [CoroutineScope::class])
    fun userScopedCoroutineScope(): CoroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob())

    companion object {

        const val KEY_PROPERTY_USER_DATA = "key_property_user_data"
    }
}