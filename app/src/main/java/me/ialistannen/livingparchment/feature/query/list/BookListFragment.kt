package me.ialistannen.livingparchment.feature.query.list

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_book_query_list.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseFragment
import me.ialistannen.livingparchment.feature.query.QueryNavigator
import java.text.DateFormat
import javax.inject.Inject

class BookListFragment : BaseFragment(), BookListFragmentContract.View {

    @Inject
    override lateinit var presenter: BookListFragmentContract.Presenter

    private var books: List<Book> = emptyList()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_book_query_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (books.isNotEmpty()) {
            book_list.books = books
            book_list.setClickListener { presenter.bookSelected(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.fragment_book_list_action_bar, menu)

        val view = menu.findItem(R.id.app_bar_search).actionView as SearchView
        view.queryHint = getString(android.R.string.search_go)

        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                book_list.filter(getBookFilter(query))
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                book_list.filter(getBookFilter(newText))
                return true
            }
        })
    }

    /**
     * Sets the books this fragment displays. Yes, this is not restored by android, but it can
     * probably be too large (i.e. > 1MB).
     *
     * @param books the books to display
     */
    fun setBooks(books: List<Book>) {
        if (book_list != null) {
            book_list.books = books
        } else {
            this.books = books
        }
    }

    override fun displayDetailPage(book: Book) {
        val navigator = activity as? QueryNavigator

        if (navigator == null) {
            Toast.makeText(
                    activity,
                    "Attached to wrong activity, not a navigator",
                    Toast.LENGTH_LONG
            ).show()
            return
        }

        navigator.displayDetailPage(book)
    }

    private fun getBookFilter(containedText: String): (Book) -> Boolean {
        if (containedText.isBlank()) {
            return { true }
        }
        return { book ->
            when {
                book.title.isContained(containedText) -> true
                book.genre.any { it.isContained(containedText) } -> true
                book.authors.any { it.isContained(containedText) } -> true
                book.isbn.isContained(containedText) -> true
                book.language.isContained(containedText) -> true
                book.pageCount.toString().isContained(containedText) -> true
                book.publisher.isContained(containedText) -> true
                formatPublishedDate(book).isContained(containedText) -> true
                book.extra.containsString(containedText) -> true
                else -> false
            }
        }
    }

    private fun formatPublishedDate(book: Book) =
            DateFormat.getDateInstance(DateFormat.LONG).format(book.published)

    private fun Map<String, Any>.containsString(string: String): Boolean {
        for ((_, value) in entries) {
            if (value is Iterable<*>) {
                if (value.any { it.toString().isContained(string) }) {
                    return true
                }
            } else if (value.toString().isContained(string)) {
                return true
            }
        }

        return false
    }

    private fun String.isContained(other: String): Boolean {
        return this
                .replace(CLEANUP_REGEX, "")
                .contains(
                        other.replace(CLEANUP_REGEX, ""),
                        true
                )
    }
}

private val CLEANUP_REGEX = "[.,\\-\\s?!()]".toRegex()