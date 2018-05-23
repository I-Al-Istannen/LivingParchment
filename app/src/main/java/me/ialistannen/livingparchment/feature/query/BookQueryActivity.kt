package me.ialistannen.livingparchment.feature.query

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_book_query.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseActivity
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

    override fun onSupportNavigateUp(): Boolean {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return false
        }
        return super.onNavigateUp()
    }

    override fun displayDetailPage(book: Book) {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(fragment_container.id, BookDetailFragment.forBook(book))
                .commit()
    }

    override fun displayResults(books: List<Book>) {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(fragment_container.id, BookListFragment().apply { setBooks(books) })
                .commit()
    }

    override fun displayGenericError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
