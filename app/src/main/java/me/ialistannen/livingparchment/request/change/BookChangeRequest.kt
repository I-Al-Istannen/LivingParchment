package me.ialistannen.livingparchment.request.change

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpPatch
import me.ialistannen.livingparchment.common.api.response.BookPatchResponse
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.common.serialization.toJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig
import java.net.URLEncoder

class BookChangeRequest(
        serverConfig: ServerConfig,
        private val isbn: String,
        private val changes: Map<String, Any>
) : BaseRequest<BookPatchResponse>(serverConfig) {

    override fun buildRequest(): Request {
        return baseUrl()
                .httpPatch()
                .header("Content-Type" to "application/json")
                .body(changes.toJson()!!)
    }

    override fun parse(responseString: String): BookPatchResponse = responseString.fromJson()

    private fun baseUrl(): String {
        return serverConfig.url + "/add/patch?isbn=${URLEncoder.encode(isbn, "utf-8")}"
    }
}