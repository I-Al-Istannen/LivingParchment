package me.ialistannen.livingparchment.feature.edit

import android.util.Log
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.async
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.common.model.BookLocation
import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.request.Requestor
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.change.BookChangeRequest
import me.ialistannen.livingparchment.request.query.QueryBookLocationRequest
import me.ialistannen.livingparchment.util.BookChangeListeners
import javax.inject.Inject

class EditScreenPresenter @Inject constructor(
        private val view: EditScreenContract.View,
        private val requestor: Requestor,
        private val serverConfig: ServerConfig
) : BasePresenter(), EditScreenContract.Presenter {

    private var displayedBook: Book? = null
    private var properties: List<EditableProperty> = emptyList()
    private var locations: List<BookLocation> = emptyList()

    override fun setBook(book: Book) {
        displayedBook = book

        properties = buildProperties(book)
        view.displayProperties(properties)
    }

    override fun addLocationRequested() {
        async(job) {
            requestor.executeRequest(QueryBookLocationRequest(serverConfig))
        } flattenUi {
            when (it) {
                is Result.Success -> {
                    locations = it.value.locations
                    view.displayLocations(it.value.locations.map { it.name })
                }
                is Result.Failure -> {
                    val exception = it.getException()
                    Log.i("EditScreenPresenter", "Error getting locations", exception)
                    view.displayMessage(exception.localizedMessage)
                }
            }
        }
    }

    override fun setLocation(location: String?) {
        if (location == null) {
            properties = properties.filterNot { it.name == "location" }

            view.displayProperties(properties)
            return
        }

        locations.firstOrNull { it.name == location }?.let {
            var locationProperty = properties.find { it.name == "location" }
            if (locationProperty == null) {
                locationProperty = SimpleEditableProperty(
                        "location", displayedBook!!.location?.name ?: "", { it }
                )
                properties += locationProperty
            }

            locationProperty.setValueFromString(it.name)

            view.displayProperties(properties)
        }
    }

    override fun commit() {
        if (displayedBook == null) {
            return
        }
        async(job) {
            val changes = properties
                    .filter { it.getValue() != null }
                    .map { it.name to it.getValue()!! }
                    .toMap()
            requestor.executeRequest(
                    BookChangeRequest(serverConfig, displayedBook!!.isbn, changes)
            )
        } flattenUi {
            when (it) {
                is Result.Success -> {
                    view.displayPatchStatus(it.value.status)
                    displayedBook = it.value.book
                }
                is Result.Failure -> {
                    val exception = it.getException()
                    view.displayMessage(exception.localizedMessage)
                    Log.i("EditScreenPresenter", "Error patching book", exception)
                }
            }

            displayedBook?.let { BookChangeListeners.booksChanged(it) }
        }
    }

    private fun buildProperties(book: Book): List<EditableProperty> {
        val properties = mutableListOf(
                SimpleEditableProperty("title", book.title, { it }),
                SimpleEditableProperty("publisher", book.publisher, { it }),
                SimpleEditableProperty("pageCount", book.pageCount, { it.toInt() }),
                listProperty("genre", book.genre),
                listProperty("authors", book.authors)
        )

        if (book.location != null) {
            properties.add(SimpleEditableProperty("location", book.location!!.name, { it }))
        }
        if ("description" in book.extra) {
            properties.add(SimpleEditableProperty(
                    "description",
                    book.extra["description"] as String,
                    { it }
            ))
        }

        return properties
    }

    private fun listProperty(name: String, value: List<String>): SimpleEditableProperty<List<String>> {
        return SimpleEditableProperty(
                name,
                value,
                { it.split(", ").map(String::trim) },
                { it.joinToString(", ") }
        )
    }

    private class SimpleEditableProperty<T>(
            override val name: String,
            private var value: T,
            private val converter: (String) -> T,
            private val toStringFunction: (T) -> String = { it.toString() }
    ) : EditableProperty {

        override fun getValueAsString(): String {
            return toStringFunction.invoke(value)
        }

        override fun getValue(): Any? = value

        override fun setValueFromString(value: String) {
            this.value = converter.invoke(value)
        }
    }
}