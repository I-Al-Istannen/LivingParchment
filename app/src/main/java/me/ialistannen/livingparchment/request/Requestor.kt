package me.ialistannen.livingparchment.request

import awaitResponse
import com.github.kittinunf.result.Result
import javax.inject.Inject

class Requestor @Inject constructor() {

    /**
     * Executes a given request.
     *
     * @receiver the request to execute
     * @return the result or an error
     */
    suspend fun <T : Any> executeRequest(baseRequest: BaseRequest<T>): Result<T, Exception> {
        val request = baseRequest.buildRequest()

        try {
            val (_, response, result) = request.awaitResponse()

            if (result is Result.Failure) {
                return Result.error(result.getException())
            }

            return baseRequest.handleResponse(response)
        } catch (e: Exception) {
            return Result.error(e)
        }
    }
}