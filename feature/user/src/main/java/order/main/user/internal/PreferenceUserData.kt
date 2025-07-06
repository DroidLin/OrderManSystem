@file:OptIn(ExperimentalSerializationApi::class)

package order.main.user.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@Serializable
internal data class PreferenceUserData(
    @ProtoNumber(1) val userId: Long = 0L,
    @ProtoNumber(2) val isActive: Boolean? = null,
) {

    companion object {

        val Empty = PreferenceUserData(
            userId = 0L,
            isActive = null,
        )
    }
}
