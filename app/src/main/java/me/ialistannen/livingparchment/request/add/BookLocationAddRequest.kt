package me.ialistannen.livingparchment.request.add

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpPut
import me.ialistannen.livingparchment.common.api.response.BookLocationAddResponse
import me.ialistannen.livingparchment.common.model.BookLocation
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.common.serialization.toJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig

class BookLocationAddRequest(
        serverConfig: ServerConfig,
        private val bookLocation: BookLocation
) : BaseRequest<BookLocationAddResponse>(serverConfig) {

    override fun buildRequest(): Request {
        return baseUrl()
                .httpPut()
                .header("Content-Type" to "application/json")
                .body(bookLocation.toJson()!!)
    }

    override fun parse(responseString: String): BookLocationAddResponse = responseString.fromJson()

    private fun baseUrl(): String = serverConfig.url + "/locations"
}