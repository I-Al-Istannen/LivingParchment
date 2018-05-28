package me.ialistannen.livingparchment.feature.delete

import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.async
import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.request.Requestor
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.delete.DeleteRequest
import javax.inject.Inject

class DeleteScreenPresenter @Inject constructor(
        private val view: DeleteScreenContract.View,
        private val requestor: Requestor,
        private val serverConfig: ServerConfig
) : BasePresenter(), DeleteScreenContract.Presenter {

    override fun delete(isbn: String) {
        async(job) {
            requestor.executeRequest(DeleteRequest(serverConfig, isbn))
        } flattenUi {
            when (it) {
                is Result.Success -> {
                    view.showStatus(it.value.status)
                    view.setIsbnInputText(it.value.isbn)
                }
                is Result.Failure -> {
                    view.displayMessage(it.getException().localizedMessage)
                }
            }
        }
    }
}