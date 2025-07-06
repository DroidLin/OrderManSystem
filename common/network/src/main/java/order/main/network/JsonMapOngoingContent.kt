package order.main.network

import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import org.json.JSONObject

class JsonMapOngoingContent(
    builder: MutableMap<String, String>.() -> Unit
) : OutgoingContent.ByteArrayContent() {

    private val content = JSONObject(
        mutableMapOf<String, String>().apply(builder)
    )
        .toString()
        .toByteArray()

    override val contentLength: Long? get() = content.size.toLong()
    override val contentType: ContentType? get() = ContentType.Application.Json
    override fun bytes(): ByteArray = content
}