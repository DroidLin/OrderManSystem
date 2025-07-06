package order.main.login.ui.internal.model

import order.main.user.data.UserAccount
import order.main.user.data.UserInfo

data class LoginPasswordResponse(val userAccount: UserAccount, val userInfo: UserInfo)