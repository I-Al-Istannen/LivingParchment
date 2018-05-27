package me.ialistannen.livingparchment.request

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import me.ialistannen.livingparchment.common.serialization.fromJson
import java.nio.charset.Charset
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
            val (_, response, exception) = request.awaitResponse()

            if (exception != null) {
                return Result.error(extractErrorFromResponse(response) ?: exception)
            }

            return baseRequest.handleResponse(response)
        } catch (e: Exception) {
            return Result.error(e)
        }
    }
}

/**
 * A small wrapper around `response` that also return the response when an error occurs.
 *
 * @return the request, the response and an exception, if any
 */
private suspend fun Request.awaitResponse(): Triple<Request, Response, FuelError?> {
    return suspendCancellableCoroutine { continuation ->
        // copied from their impl, I have no idea if they do funky things with their coroutines,
        // so better be safe than sorry
        continuation.invokeOnCompletion {
            if (continuation.isCancelled) {
                continuation.cancel()
            }
        }

        response { request, response, result ->
            result.fold(
                    {
                        continuation.resume(Triple(request, response, null))
                    },
                    {
                        continuation.resume(Triple(request, response, it))
                    }
            )
        }
    }
}

private fun extractErrorFromResponse(response: Response?): Exception? {
    if (response != null && response.data.isNotEmpty()) {
        val returnedData = response.data.toString(Charset.defaultCharset())
        val errorResponse = returnedData.fromJson<ErrorResponsePojo>()
        return RuntimeException(
                "Error: ${errorResponse.errors?.joinToString(", ")} (${response.statusCode})"
        )
    }
    return null
}

private data class ErrorResponsePojo(val errors: List<String>?)