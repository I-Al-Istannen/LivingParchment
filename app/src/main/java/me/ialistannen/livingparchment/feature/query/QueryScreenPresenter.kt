package me.ialistannen.livingparchment.feature.query

import android.util.Log
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.async
import me.ialistannen.livingparchment.common.api.query.QueryType
import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.request.Requestor
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.query.QueryBookRequest
import javax.inject.Inject

class QueryScreenPresenter @Inject constructor(
        private val view: QueryScreenContract.View,
        private val requestor: Requestor,
        private val serverConfig: ServerConfig
) : BasePresenter(), QueryScreenContract.Presenter {

    override fun runQuery(queryType: QueryType, attribute: String, query: String) {
        async(job) {
            requestor.executeRequest(QueryBookRequest(serverConfig, queryType, attribute, query))
        } flattenUi {
            when (it) {
                is Result.Success -> view.displayResults(it.value.books)
                is Result.Failure -> {
                    view.displayGenericError(it.getException().localizedMessage)
                    Log.w("hey", it.getException())
                }
            }
        }
    }
}