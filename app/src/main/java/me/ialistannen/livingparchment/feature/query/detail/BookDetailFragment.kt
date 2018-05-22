package me.ialistannen.livingparchment.feature.query.detail

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_book_detail.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.common.serialization.toJson
import me.ialistannen.livingparchment.feature.BaseFragment
import java.text.DateFormat
import javax.inject.Inject

class BookDetailFragment : BaseFragment(), BookDetailFragmentContract.View {

    companion object {

        @JvmStatic
        fun forBook(book: Book): BookDetailFragment {
            return BookDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("book", book.toJson())
                }
            }
        }
    }

    @Inject
    override lateinit var presenter: BookDetailFragmentContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        book_detail_list.adapter = BookDetailAdapter()
        book_detail_list.layoutManager = LinearLayoutManager(activity)
        book_detail_list.addItemDecoration(
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        book_detail_list.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.top += 20
                }
                outRect.bottom += 20
            }
        })

        if (arguments.containsKey("book")) {
            val book = arguments.getString("book").fromJson<Book>()
            setBook(book)
        }
    }

    private fun setBook(book: Book) {
        println("SET!")
        val dataList: MutableList<Pair<String, String>> = mutableListOf(
                "title" to book.title,
                "authors" to book.authors.joinToString("\n"), // position two
                "isbn" to book.isbn,
                "language" to book.language,
                "page_count" to book.pageCount.toString(),
                "publisher" to book.publisher,
                "published" to DateFormat.getDateInstance(DateFormat.LONG).format(book.published)
        )
        for ((key, value) in book.extra) {
            if (key == "authors") {
                continue
            }
            if (value is List<*>) {
                if (value.isNotEmpty()) {
                    dataList.add(key to value.joinToString(", "))
                }
            } else {
                dataList.add(key to value.toString())
            }
        }

        (book_detail_list.adapter as BookDetailAdapter).data = dataList.map {
            it.first.translateIfPossible() to it.second
        }
        println("Done")
    }

    private fun String.translateIfPossible(): String {
        val identifier = resources.getIdentifier(
                "fragment_book_detail_$this",
                "string",
                activity.packageName
        )

        if (identifier == 0) {
            return this.capitalize()
        }

        return resources.getString(identifier)
    }

    private class BookDetailAdapter : RecyclerView.Adapter<BookDetailViewHolder>() {

        var data: List<Pair<String, String>> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
                println("Set to $value")
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookDetailViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.book_detail_viewholder_view, parent, false)

            return BookDetailViewHolder(view)
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: BookDetailViewHolder, position: Int) {
            holder.bind(data[position])
        }

    }

    private class BookDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val keyView: TextView = view.findViewById(R.id.book_detail_key)
        private val valueView: TextView = view.findViewById(R.id.book_detail_value)

        fun bind(data: Pair<String, String>) {
            keyView.text = data.first
            valueView.text = data.second
        }
    }
}