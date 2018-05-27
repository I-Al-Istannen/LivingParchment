package me.ialistannen.livingparchment.request.query

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet
import me.ialistannen.livingparchment.common.api.query.QueryType
import me.ialistannen.livingparchment.common.api.response.BookResponse
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig

class QueryBookRequest(
        serverConfig: ServerConfig,
        private val queryType: QueryType,
        private val attribute: String,
        query: String
) : BaseRequest<BookResponse>(serverConfig) {

    private val query = if (query.isEmpty()) " " else query

    override fun buildRequest(): Request {
        return baseUrl()
                .httpGet(listOf(
                        "queryType" to queryType.name,
                        "queryString" to query,
                        "attributeName" to attribute
                ))
    }

    override fun parse(responseString: String): BookResponse = responseString.fromJson()

    private fun baseUrl(): String = serverConfig.url + "/query"
}