package me.ialistannen.livingparchment.request

import android.util.Log
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.runBlocking
import me.ialistannen.livingparchment.common.api.response.LoginResponse
import me.ialistannen.livingparchment.common.api.response.LoginStatus
import me.ialistannen.livingparchment.request.auth.LoginRequest
import me.ialistannen.livingparchment.request.auth.UserCredentials
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

internal class Authenticator(
        private val requestor: Requestor,
        private val userCredentials: UserCredentials
) {

    private var token: String = ""
    private var lastUpdate: Long = 0
    private val lock: Lock = ReentrantLock()

    fun authenticationNeeded(response: Response): Boolean {
        return response.statusCode == 401
    }

    fun authenticate(serverConfig: ServerConfig) {
        // prevent concurrent authentications
        lock.lock()
        if ((System.nanoTime() - lastUpdate) < TimeUnit.MILLISECONDS.toNanos(1000)) {
            return
        }

        try {
            runBlocking {
                val loginRequest = LoginRequest(
                        serverConfig, userCredentials.name, userCredentials.password
                )
                val result = requestor.executeRequest(loginRequest)

                when (result) {
                    is Result.Success -> {
                        handleLoginResponse(result.value)
                    }
                    is Result.Failure -> {
                        val exception = result.getException()

                        Log.w("Authenticator", "Error logging in", exception)
                        throw RuntimeException("Error logging in: ${exception.localizedMessage}")
                    }
                }
            }
            lastUpdate = System.nanoTime()
        } finally {
            lock.unlock()
        }
    }

    private fun handleLoginResponse(loginResponse: LoginResponse) {
        // TODO: this should be handled in the view layer and not here.
        when (loginResponse.status) {
            LoginStatus.AUTHENTICATED -> token = loginResponse.token!!
            LoginStatus.INVALID_CREDENTIALS -> throw RuntimeException("Invalid credentials")
            LoginStatus.INTERNAL_ERROR -> throw RuntimeException("Internal error")
        }
    }

    /**
     * Adjusts the request to use authentication.
     */
    fun modifyRequest(request: Request): Request {
        return request.header("Authorization" to "Bearer $token")
    }
}