package me.ialistannen.livingparchment.feature.query

import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_book_query.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.query.QueryType
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseActivity
import me.ialistannen.livingparchment.feature.edit.EditScreenFragment
import me.ialistannen.livingparchment.feature.query.detail.BookDetailFragment
import me.ialistannen.livingparchment.feature.query.list.BookListFragment
import me.ialistannen.livingparchment.feature.query.query.QueryFragment
import javax.inject.Inject

class BookQueryActivity : BaseActivity(), QueryScreenContract.View, QueryNavigator {

    @Inject
    override lateinit var presenter: QueryScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_query)

        setSupportActionBar(actionbar as Toolbar)

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(fragment_container.id, QueryFragment())
                    .commit()
        }

        supportActionBar?.let {
            it.title = getString(R.string.activity_query_title)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun displayDetailPage(book: Book) {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(fragment_container.id, BookDetailFragment.forBook(book))
                .commit()
    }

    override fun displayResults(books: List<Book>, queryType: QueryType, attribute: String, query: String) {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(fragment_container.id, BookListFragment.forQuery(
                        queryType, attribute, query, books
                ))
                .commit()
    }

    override fun displayEditPage(book: Book) {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(fragment_container.id, EditScreenFragment.forBook(book))
                .commit()
    }
}
