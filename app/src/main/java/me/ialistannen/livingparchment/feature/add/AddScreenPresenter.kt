package me.ialistannen.livingparchment.feature.add

import android.util.Log
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.async
import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.request.Requestor
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.add.AddBookRequest
import javax.inject.Inject

class AddScreenPresenter @Inject constructor(
        private val view: AddScreenContract.View,
        private val requestor: Requestor,
        private val serverConfig: ServerConfig
) : BasePresenter(), AddScreenContract.Presenter {

    override fun add(isbn: String) {
        async(job) {
            requestor.executeRequest(AddBookRequest(serverConfig, isbn))
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