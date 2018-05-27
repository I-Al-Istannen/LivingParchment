package me.ialistannen.livingparchment.request.query

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet
import me.ialistannen.livingparchment.common.api.response.BookLocationQueryResponse
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig

class QueryBookLocationRequest(serverConfig: ServerConfig

) : BaseRequest<BookLocationQueryResponse>(serverConfig) {


    override fun buildRequest(): Request {
        return baseUrl()
                .httpGet()
    }

    override fun parse(responseString: String): BookLocationQueryResponse = responseString.fromJson()

    private fun baseUrl(): String = serverConfig.url + "/locations"
}