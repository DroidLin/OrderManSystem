package order.main.network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody

fun <T : Any> HttpRequestBuilder.objectRequestBody(data: T) {
    setBody(JsonOngoingContent(data))
}

fun HttpRequestBuilder.jsonRequestBody(builder: MutableMap<String, String>.() -> Unit) {
    setBody(JsonMapOngoingContent(builder))
}
