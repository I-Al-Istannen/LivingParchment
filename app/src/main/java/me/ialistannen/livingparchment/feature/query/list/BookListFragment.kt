package me.ialistannen.livingparchment.feature.query.list

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        val adapter = BookListAdapter()
        book_list.adapter = adapter
        book_list.layoutManager = LinearLayoutManager(activity)
        book_list.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        if (books.isNotEmpty()) {
            adapter.items = books
            adapter.clickListener = { presenter.bookSelected(it) }
            books = emptyList()
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
            (book_list.adapter as BookListAdapter).items = books
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

    private class BookListAdapter : RecyclerView.Adapter<BookListViewHolder>() {

        var items: List<Book> = arrayListOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        var clickListener: (Book) -> Unit = {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.book_list_viewholder_view, parent, false)
            return BookListViewHolder(view) { pos ->
                clickListener.invoke(items[pos])
            }
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
            holder.setBook(items[position])
        }
    }

    private class BookListViewHolder(view: View,
                                     private val clickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val titleView: TextView = view.findViewById(R.id.book_title)
        private val isbnView: TextView = view.findViewById(R.id.book_isbn)
        private val authorView: TextView = view.findViewById(R.id.book_author)

        init {
            view.setOnClickListener { clickListener.invoke(adapterPosition) }
        }

        fun setBook(book: Book) {
            titleView.text = book.title
            isbnView.text = book.isbn
            authorView.text = book.authors.joinToString(", ")
        }
    }
}