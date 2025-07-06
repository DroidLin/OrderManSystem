@file:OptIn(ExperimentalSerializationApi::class)

package order.main.user.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@Serializable
internal data class PreferenceUserInfo(
    @ProtoNumber(1) val userId: Long,
    @ProtoNumber(2) val nickname: String,
    @ProtoNumber(3) val phone: String,
    @ProtoNumber(4) val language: String?
) {
    companion object {

        val Empty = PreferenceUserInfo(
            userId = 0,
            nickname = "",
            phone = "",
            language = null
        )
    }
}
