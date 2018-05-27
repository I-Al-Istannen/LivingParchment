package me.ialistannen.livingparchment.feature.query.list

import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.async
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.request.Requestor
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.delete.DeleteRequest
import javax.inject.Inject

class BookListPresenter @Inject constructor(
        private val view: BookListFragmentContract.View,
        private val requestor: Requestor,
        private val serverConfig: ServerConfig
) : BasePresenter(), BookListFragmentContract.Presenter {

    private var books: MutableList<Book> = mutableListOf()

    override fun onViewCreated() {
        view.displayBooks(books)
    }

    override fun bookSelected(book: Book) {
        view.displayDetailPage(book)
    }

    override fun setBooks(books: List<Book>) {
        this.books = books.toMutableList()

        view.displayBooks(books)
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
                    view.displayGenericError(it.getException().localizedMessage)
                }
            }
        }
    }
}