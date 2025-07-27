package order.main.login.internal.model

import order.main.user.data.UserAccount
import order.main.user.data.UserInfo

data class UserDataResponse(val userAccount: UserAccount, val userInfo: UserInfo)