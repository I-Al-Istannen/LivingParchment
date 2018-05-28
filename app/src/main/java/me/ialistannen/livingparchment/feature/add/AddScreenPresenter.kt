package me.ialistannen.livingparchment.feature.add

import android.util.Log
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.async
import me.ialistannen.livingparchment.common.model.BookLocation
import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.request.Requestor
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.add.AddBookRequest
import me.ialistannen.livingparchment.request.query.QueryBookLocationRequest
import javax.inject.Inject

class AddScreenPresenter @Inject constructor(
        private val view: AddScreenContract.View,
        private val requestor: Requestor,
        private val serverConfig: ServerConfig
) : BasePresenter(), AddScreenContract.Presenter {

    override fun onCreate() {
        async(job) {
            requestor.executeRequest(QueryBookLocationRequest(serverConfig))
        } flattenUi {
            when (it) {
                is Result.Success -> view.setAvailableLocations(it.value.locations)
                is Result.Failure -> {
                    view.displayMessage(it.getException().localizedMessage)
                    Log.i("AddScreenPresenter", "Error getting locations", it.getException())
                }
            }
        }
    }

    override fun add(isbn: String, bookLocation: BookLocation?) {
        async(job) {
            requestor.executeRequest(AddBookRequest(serverConfig, isbn, bookLocation))
        } flattenUi {
            when (it) {
                is Result.Success -> {
                    view.displayAddResult(it.value.status)
                    view.setInputIsbn(it.value.isbn)
                }
                is Result.Failure -> {
                    Log.i("AddScreenPresenter", "Error adding", it.getException())
                    view.displayAddFailed(it.getException().localizedMessage)
                }
            }
        }
    }
}