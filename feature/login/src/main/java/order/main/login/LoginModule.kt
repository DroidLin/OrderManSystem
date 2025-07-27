package order.main.login

import order.main.user.UserDependencies
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [UserDependencies::class])
@ComponentScan
class LoginModule