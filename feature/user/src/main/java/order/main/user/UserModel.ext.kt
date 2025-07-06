package order.main.user

import order.main.user.data.UserAccount
import order.main.user.data.UserInfo

val UserAccount.isLogin: Boolean
    get() = this.userId > 0

val UserInfo.isLogin: Boolean
    get() = this.userId > 0
