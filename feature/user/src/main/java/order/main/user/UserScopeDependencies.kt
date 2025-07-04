package order.main.user

import order.main.user.data.UserData
import org.koin.core.annotation.Module
import org.koin.core.annotation.Property
import org.koin.core.annotation.Scope

/**
 * 需提供[AppUserScope]作用域
 */
@Module
class UserScopeDependencies {

    @UserScopedData
    @Scope(value = AppUserScope::class)
    fun userScopedData(@Property(KEY_PROPERTY_USER_DATA) userData: UserData): UserData = userData

    @Scope(value = AppUserScope::class)
    fun userData(@UserScopedData userData: UserData): UserData = userData

    companion object {

        const val KEY_PROPERTY_USER_DATA = "key_property_user_data"
    }
}