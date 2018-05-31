package me.ialistannen.livingparchment.request.change

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpPatch
import me.ialistannen.livingparchment.common.api.response.BookLocationPatchResponse
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig
import java.util.*

class BookLocationChangeRequest(serverConfig: ServerConfig,
                                private val id: UUID,
                                private val name: String,
                                private val description: String
) : BaseRequest<BookLocationPatchResponse>(serverConfig) {

    override fun buildRequest(): Request {
        return baseUrl()
                .httpPatch(listOf(
                        "id" to id.toString(),
                        "name" to name,
                        "description" to description
                ))
    }

    override fun parse(responseString: String): BookLocationPatchResponse = responseString.fromJson()

    private fun baseUrl() = serverConfig.url + "/locations"
}