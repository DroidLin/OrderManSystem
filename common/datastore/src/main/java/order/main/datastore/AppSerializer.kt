@file:OptIn(ExperimentalSerializationApi::class)

package order.main.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import okio.BufferedSink
import okio.BufferedSource
import java.io.InputStream
import java.io.OutputStream

class AppSerializer<T : Any>(
    override val defaultValue: T,
    argumentSerializer: List<KSerializer<*>> = emptyList(),
    isNullable: Boolean = false
) : Serializer<T> {

    private val serializer = ProtoBuf.Default.serializersModule.serializer(
        kClass = defaultValue::class,
        typeArgumentsSerializers = argumentSerializer,
        isNullable = isNullable,
    ) as KSerializer<T>

    override suspend fun readFrom(input: InputStream): T {
        val bytes = input.readBytes()
        return ProtoBuf.Default.decodeFromByteArray(serializer, bytes)
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        val data = ProtoBuf.Default.encodeToByteArray(serializer, t)
        output.write(data)
        output.flush()
    }
}