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
    open fun handleResponse(response: Response): Result<T, Exception> {
        return try {
            val addResponse = parse(String(response.data))
            Result.Success(addResponse)
        } catch (e: Exception) {
            Result.error(e)
        }
    }

    /**
     * Parses the response string to the returned object.
     *
     * @param responseString the response string
     * @throws Exception if anything happens, it will be wrapped appropriately
     */
    protected abstract fun parse(responseString: String): T
}