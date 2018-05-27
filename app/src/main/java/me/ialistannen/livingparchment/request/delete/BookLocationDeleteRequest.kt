package me.ialistannen.livingparchment.request.delete

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpDelete
import me.ialistannen.livingparchment.common.api.response.BookLocationDeleteResponse
import me.ialistannen.livingparchment.common.model.BookLocation
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig

class BookLocationDeleteRequest(
        serverConfig: ServerConfig,
        private val bookLocation: BookLocation
) : BaseRequest<BookLocationDeleteResponse>(serverConfig) {

    override fun buildRequest(): Request {
        return baseUrl()
                .httpDelete(listOf("id" to bookLocation.uuid.toString()))
    }

    override fun parse(responseString: String): BookLocationDeleteResponse =
            responseString.fromJson()

    private fun baseUrl(): String = serverConfig.url + "/locations"
}