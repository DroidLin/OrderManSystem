package order.main.user.internal

import order.main.user.data.UserAccount
import order.main.user.data.UserInfo


internal fun PreferenceUserData.toUserData(): UserAccount {
    return UserAccount(
        userId = this.userId,
        accessToken = accessToken
    )
}

internal fun UserAccount.toPreference(): PreferenceUserData {
    return PreferenceUserData(
        userId = this.userId,
        accessToken = accessToken
    )
}

internal fun PreferenceUserInfo.toUserInfo(): UserInfo {
    return UserInfo(
        userId = this.userId,
        nickname = this.nickname,
        phone = this.phone,
        language = this.language
    )
}

internal fun UserInfo.toPreference(): PreferenceUserInfo {
    return PreferenceUserInfo(
        userId = this.userId,
        nickname = this.nickname,
        phone = this.phone,
        language = this.language
    )
}
