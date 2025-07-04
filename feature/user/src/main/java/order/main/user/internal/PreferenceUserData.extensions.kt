package order.main.user.internal

import order.main.user.data.UserData


internal fun PreferenceUserData.toUserData(): UserData {
    return UserData(id = this.id)
}