package me.ialistannen.livingparchment.feature.query.list

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.model.Book
import kotlin.properties.Delegates

class MultipleBookRecyclerList : RecyclerView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    var books: List<Book>
        set(value) {
            adapter.items = value
        }
        get() = adapter.items

    private var adapter: BookListAdapter
        get() = getAdapter() as BookListAdapter
        set(value) = setAdapter(value)

    init {
        adapter = BookListAdapter()
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    /**
     * Sets the filter to use
     *
     * @param predicate the filter
     */
    fun filter(predicate: (Book) -> Boolean) {
        adapter.predicate = predicate
    }

    /**
     * Sets the click listener that fires when an item was selected.
     *
     * @param clickListener the listener
     */
    fun setClickListener(clickListener: (Book) -> Unit) {
        adapter.clickListener = clickListener
    }

    private class BookListAdapter : RecyclerView.Adapter<BookListViewHolder>() {

        var items: List<Book> = emptyList()
            set(value) {
                field = value
                runFilter(predicate)
            }
        private var filtered = items
        var predicate: (Book) -> Boolean by Delegates.observable({ _ -> true }) { _, _, newValue ->
            runFilter(newValue)
        }
        var clickListener: (Book) -> Unit = {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.book_list_viewholder_view, parent, false)
            return BookListViewHolder(view) { pos ->
                clickListener.invoke(filtered[pos])
            }
        }

        override fun getItemCount(): Int = filtered.size

        override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
            holder.setBook(filtered[position])
        }

        private fun runFilter(predicate: (Book) -> Boolean) {
            filtered = items.filter(predicate)
            notifyDataSetChanged()
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