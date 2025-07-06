@file:OptIn(ExperimentalSerializationApi::class)

package order.main.network

import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class JsonOngoingContent<T : Any>(
    val data: T,
) : OutgoingContent.ByteArrayContent() {

    private val content = Json.encodeToString(
        value = data,
        serializer = serializer(
            kClass = data::class,
            typeArgumentsSerializers = emptyList(),
            isNullable = false
        )
    ).toByteArray()

    override val contentLength: Long? get() = content.size.toLong()
    override val contentType: ContentType? get() = ContentType.Application.Json
    override fun bytes(): ByteArray = content
}