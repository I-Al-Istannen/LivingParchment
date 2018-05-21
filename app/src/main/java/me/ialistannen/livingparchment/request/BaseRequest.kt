package me.ialistannen.livingparchment.request

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result

abstract class BaseRequest<out T : Any>(protected val serverConfig: ServerConfig) {

    /**
     * Builds the request to send.
     */
    abstract fun buildRequest(): Request

    /**
     * Maps the response to the correct type.
     *
     * @param response the response the server returned
     * @return the
     */
    abstract fun handleResponse(response: Response): Result<T, Exception>
}