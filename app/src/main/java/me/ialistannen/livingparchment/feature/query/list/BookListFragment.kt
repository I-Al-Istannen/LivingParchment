package me.ialistannen.livingparchment.feature.query.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_book_query_list.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseFragment
import me.ialistannen.livingparchment.feature.query.QueryNavigator
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (books.isNotEmpty()) {
            book_list.books = books
            book_list.setClickListener { presenter.bookSelected(it) }
        }
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
}