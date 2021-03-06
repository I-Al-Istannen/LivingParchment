package me.ialistannen.livingparchment.feature.query.detail

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_book_detail.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.common.serialization.toJson
import me.ialistannen.livingparchment.feature.BaseFragment
import me.ialistannen.livingparchment.feature.query.QueryNavigator
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.util.BookChangeListener
import me.ialistannen.livingparchment.util.BookChangeListeners
import me.ialistannen.livingparchment.util.addSpacingDecoration
import java.lang.Exception
import java.text.DateFormat
import javax.inject.Inject

class BookDetailFragment : BaseFragment(), BookDetailFragmentContract.View, BookChangeListener {

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

    @Inject
    lateinit var serverConfig: ServerConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        book_detail_list.adapter = BookDetailAdapter()
        book_detail_list.layoutManager = LinearLayoutManager(activity)
        book_detail_list.addItemDecoration(
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        book_detail_list.addSpacingDecoration()

        if (arguments.containsKey("book")) {
            val book = arguments.getString("book").fromJson<Book>()
            presenter.setBook(book)
        }

        setActionbarTitle(getString(R.string.fragment_book_detail_fragment_title))

        BookChangeListeners.addListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_book_detail_action_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_book_menu_item -> {
                presenter.editRequested()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // This should not be in the view, but it is recreated from the fragment arguments. So it sadly
    // needs to be in here.
    override fun onBookChanged(books: List<Book>) {
        if (arguments.containsKey("book")) {
            val thisBook = arguments.getString("book").fromJson<Book>()

            books.firstOrNull { it.isbn == thisBook.isbn }?.let {
                arguments.putString("book", it.toJson())
            }
        }
    }

    override fun displayBook(book: Book) {
        val dataList: MutableList<Pair<String, String>> = mutableListOf(
                "title" to book.title,
                "authors" to book.authors.joinToString("\n"), // position two
                "isbn" to book.isbn,
                "language" to book.language,
                "page_count" to book.pageCount.toString(),
                "publisher" to book.publisher,
                "published" to DateFormat.getDateInstance(DateFormat.LONG).format(book.published)
        )
        if (book.location != null) {
            dataList.add("location" to book.location!!.name)
        }
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

        (book_detail_list.adapter as BookDetailAdapter).apply {
            imageUrl = "${serverConfig.url}/covers/${book.isbn}.jpg"
            data = dataList.map { it.first.translateIfPossible() to it.second }
        }
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

    override fun displayEditScreen(book: Book) {
        (activity as? QueryNavigator)?.displayEditPage(book)
    }

    private class BookDetailAdapter : RecyclerView.Adapter<BaseViewHolder>() {

        var data: List<Pair<String, String>> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        var imageUrl: String = ""

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            return if (viewType == BookDetailViewHolder.viewType) {
                val view = inflater.inflate(R.layout.book_detail_viewholder_view, parent, false)
                BookDetailViewHolder(view)
            } else {
                val view = inflater.inflate(R.layout.book_detail_viewholder_image_view, parent, false)
                ImageViewHolder(view)
            }
        }

        override fun getItemCount(): Int = data.size

        override fun getItemViewType(position: Int): Int {
            return if (position == 0) ImageViewHolder.viewType else {
                BookDetailViewHolder.viewType
            }
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            when (holder) {
                is BookDetailViewHolder -> holder.bind(data[position])
                is ImageViewHolder -> holder.setImage(imageUrl)
            }
        }

    }

    private class BookDetailViewHolder(view: View) : BaseViewHolder(view) {

        companion object {
            const val viewType: Int = 0
        }


        private val keyView: TextView = view.findViewById(R.id.book_detail_key)
        private val valueView: TextView = view.findViewById(R.id.book_detail_value)

        fun bind(data: Pair<String, String>) {
            keyView.text = data.first
            valueView.text = data.second
        }
    }

    private class ImageViewHolder(view: View) : BaseViewHolder(view) {

        companion object {
            const val viewType: Int = 1
        }

        private val imageView: ImageView = view.findViewById(R.id.image_view)

        fun setImage(url: String) {
            Picasso.get().load(url)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.book_cover_placeholder)
                    .error(R.drawable.book_cover_placeholder)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            imageView.visibility = View.VISIBLE
                        }

                        override fun onError(e: Exception?) {
                            imageView.visibility = View.GONE
                        }
                    })

        }
    }

    private abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)
}