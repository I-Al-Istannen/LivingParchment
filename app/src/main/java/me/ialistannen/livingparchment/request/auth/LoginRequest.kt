package me.ialistannen.livingparchment.request.auth

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpPost
import me.ialistannen.livingparchment.common.api.response.LoginResponse
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.request.BaseRequest
import me.ialistannen.livingparchment.request.ServerConfig

class LoginRequest(serverConfig: ServerConfig,
                   private val username: String,
                   private val password: String) : BaseRequest<LoginResponse>(serverConfig) {

    override fun buildRequest(): Request {
        return baseUrl()
                .httpPost(listOf("name" to username, "password" to password))
    }

    override fun parse(responseString: String): LoginResponse = responseString.fromJson()

    private fun baseUrl() = serverConfig.url + "/login"
}