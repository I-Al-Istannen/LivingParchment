package me.ialistannen.livingparchment.feature.query.list

import android.util.Log
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.async
import me.ialistannen.livingparchment.common.api.query.QueryType
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.request.Requestor
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.delete.DeleteRequest
import me.ialistannen.livingparchment.request.query.QueryBookRequest
import me.ialistannen.livingparchment.util.BookChangeListener
import me.ialistannen.livingparchment.util.BookChangeListeners
import javax.inject.Inject

class BookListPresenter @Inject constructor(
        private val view: BookListFragmentContract.View,
        private val requestor: Requestor,
        private val serverConfig: ServerConfig
) : BasePresenter(), BookListFragmentContract.Presenter, BookChangeListener {

    private var books: MutableList<Book> = mutableListOf()
    private lateinit var queryType: QueryType
    private lateinit var query: String
    private lateinit var attribute: String

    override fun onCreate() {
        BookChangeListeners.addListener(this)
    }

    override fun onViewCreated() {
        view.displayBooks(books)
    }

    override fun bookSelected(book: Book) {
        view.displayDetailPage(book)
    }

    override fun setBooks(books: List<Book>?) {
        if (books == null && this.books.isEmpty()) {
            onRefreshRequested()
        } else if (books == null) {
            view.displayBooks(this.books)
        } else {
            this.books = books.sortedBy { it.title }.toMutableList()

            view.displayBooks(books)
        }
    }

    override fun onRefreshRequested() {
        view.setRefreshIndicator(true)

        async(job) {
            requestor.executeRequest(QueryBookRequest(serverConfig, queryType, attribute, query))
        } flattenUi {
            view.setRefreshIndicator(false)

            when (it) {
                is Result.Success -> setBooks(it.value.books.sortedBy { it.title })
                is Result.Failure -> {
                    val exception = it.getException()
                    Log.i("BookListPresenter", "Error querying books", exception)
                }
            }
        }
    }

    override fun setQuery(queryType: QueryType, attribute: String, query: String) {
        this.queryType = queryType
        this.attribute = attribute
        this.query = query
    }

    override fun deleteBook(book: Book) {
        async(job) {
            requestor.executeRequest(DeleteRequest(serverConfig, book.isbn))
        } flattenUi {
            when (it) {
                is Result.Success -> {
                    view.displayDeleteStatus(it.value.status)
                    books.remove(book)
                    view.displayBooks(books)
                }
                is Result.Failure -> {
                    Log.i("BookListPresenter", "Error deleting a book", it.getException())
                    view.displayMessage(it.getException().localizedMessage)
                }
            }
        }
    }

    override fun onBookChanged(books: List<Book>) {
        val changed: Map<String, Book> = books.map { it.isbn to it }.toMap()

        val iterator = this.books.listIterator()
        while (iterator.hasNext()) {
            val book = iterator.next()

            if (book.isbn in changed) {
                iterator.remove()
                iterator.add(changed[book.isbn]!!)
            }
        }
    }
}