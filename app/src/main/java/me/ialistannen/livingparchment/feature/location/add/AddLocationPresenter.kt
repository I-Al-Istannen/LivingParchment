package me.ialistannen.livingparchment.feature.location.add

import android.util.Log
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.async
import me.ialistannen.livingparchment.common.model.BookLocation
import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.request.Requestor
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.add.BookLocationAddRequest
import me.ialistannen.livingparchment.request.delete.BookLocationDeleteRequest
import me.ialistannen.livingparchment.request.query.QueryBookLocationRequest
import javax.inject.Inject

class AddLocationPresenter @Inject constructor(
        private val view: ManageBookLocationContract.View,
        private val requestor: Requestor,
        private val serverConfig: ServerConfig
) : BasePresenter(), ManageBookLocationContract.Presenter {

    override fun onCreate() {
        refresh()
    }

    private fun refresh() {
        async {
            requestor.executeRequest(QueryBookLocationRequest(serverConfig))
        } flattenUi {
            when (it) {
                is Result.Success -> view.displayLocations(it.value.locations.sortedBy { it.name })
                is Result.Failure -> {
                    val exception = it.getException()
                    view.displayGenericError(exception.localizedMessage)
                    Log.i("AddLocationPresenter", "Error getting locations", exception)
                }
            }
        }
    }

    override fun addLocation(name: String, description: String) {
        val bookLocation = BookLocation(name, description)
        async {
            requestor.executeRequest(BookLocationAddRequest(serverConfig, bookLocation))
        } flattenUi {
            when (it) {
                is Result.Success -> {
                    view.displayAddStatus(it.value.status)
                    refresh()
                }
                is Result.Failure -> {
                    val exception = it.getException()
                    view.displayGenericError(exception.localizedMessage)
                    Log.i("AddLocationPresenter", "Error adding a location", exception)
                }
            }
        }
    }

    override fun deleteLocation(bookLocation: BookLocation) {
        async {
            requestor.executeRequest(BookLocationDeleteRequest(serverConfig, bookLocation))
        } flattenUi {
            when (it) {
                is Result.Success -> {
                    view.displayDeleteStatus(it.value.status)
                    refresh()
                }
                is Result.Failure -> {
                    val exception = it.getException()
                    view.displayGenericError(exception.localizedMessage)
                    Log.i("AddLocationPresenter", "Error deleting a location", exception)
                }
            }
        }
    }
}