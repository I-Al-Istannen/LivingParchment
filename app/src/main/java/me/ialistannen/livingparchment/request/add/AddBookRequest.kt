package me.ialistannen.livingparchment.request.add

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpPut
import me.ialistannen.livingparchment.common.api.response.BookAddResponse
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig

class AddBookRequest(serverConfig: ServerConfig,
                     private val isbn: String
) : BaseRequest<BookAddResponse>(serverConfig) {

    override fun buildRequest(): Request {
        return baseUrl()
                .httpPut(listOf("isbn" to isbn))
    }

    override fun parse(responseString: String): BookAddResponse = responseString.fromJson()

    private fun baseUrl(): String {
        return serverConfig.url + "/add"
    }
}