package me.ialistannen.livingparchment.feature

import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

/**
 * A coroutine holder that is bound to a lifecycle.
 */
interface CoroutineHolder {

    val job: Job

    /**
     * Runs a piece of code on the UI thread and transforms the deferred value on it.
     *
     * @param consumer the consumer of the computation
     */
    infix fun <T : Any> Deferred<T>.ui(consumer: suspend (Result<T, Exception>) -> Unit) {
        launch(job + UI) {
            try {
                consumer.invoke(Result.of(await()))
            } catch (e: Exception) {
                consumer.invoke(Result.error(e))
            }
        }
    }

    /**
     * Runs a piece of code on the UI thread and transforms the deferred value on it.
     *
     * @param consumer the consumer of the computation
     */
    infix fun <T : Any, E : Exception> Deferred<Result<T, E>>.flattenUi(
            consumer: suspend (Result<T, Exception>) -> Unit) {

        return ui { consumer.invoke(it.unwrap()) }
    }

    private fun <A : Any, B : Exception> Result<Result<A, B>, B>.unwrap(): Result<A, B> {
        return when (this) {
            is Result.Success -> value
            is Result.Failure -> Result.error(getException())
        }
    }
}