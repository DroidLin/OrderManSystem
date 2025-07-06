package order.main.user

import order.main.user.data.UserAccount
import order.main.user.data.UserInfo

val UserAccount.isLogin: Boolean
    get() = this.userId > 0 && this.accessToken.isNotEmpty()

val UserInfo.isLogin: Boolean
    get() = this.userId > 0
