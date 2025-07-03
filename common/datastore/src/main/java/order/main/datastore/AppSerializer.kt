@file:OptIn(ExperimentalSerializationApi::class)

package order.main.datastore

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import okio.BufferedSink
import okio.BufferedSource

class AppSerializer<T : Any>(
    override val defaultValue: T,
    argumentSerializer: List<KSerializer<*>> = emptyList(),
    isNullable: Boolean = false
) : OkioSerializer<T> {

    private val serializer = ProtoBuf.Default.serializersModule.serializer(
        kClass = defaultValue::class,
        typeArgumentsSerializers = argumentSerializer,
        isNullable = isNullable,
    ) as KSerializer<T>

    override suspend fun readFrom(source: BufferedSource): T {
        val bytes = source.readByteArray()
        return ProtoBuf.Default.decodeFromByteArray(serializer, bytes)
    }

    override suspend fun writeTo(t: T, sink: BufferedSink) {
        val data = ProtoBuf.Default.encodeToByteArray(serializer, t)
        sink.write(data)
        sink.flush()
    }
}