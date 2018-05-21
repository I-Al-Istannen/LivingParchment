package me.ialistannen.livingparchment.request.delete

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpDelete
import me.ialistannen.livingparchment.common.api.response.BookDeleteResponse
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig

class DeleteRequest(serverConfig: ServerConfig,
                    private val isbn: String
) : BaseRequest<BookDeleteResponse>(serverConfig) {

    override fun buildRequest(): Request {
        return baseUrl()
                .httpDelete(listOf("isbn" to isbn))
    }

    override fun parse(responseString: String): BookDeleteResponse = responseString.fromJson()

    private fun baseUrl(): String = this.serverConfig.url + "/delete"
}