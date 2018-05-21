package me.ialistannen.livingparchment.request.add

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
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

    override fun handleResponse(response: Response): Result<BookAddResponse, Exception> {
        return try {
            val addResponse = String(response.data).fromJson<BookAddResponse>()
            Result.Success(addResponse)
        } catch (e: Exception) {
            Result.error(e)
        }
    }

    private fun baseUrl(): String {
        return serverConfig.url + "/add"
    }
}