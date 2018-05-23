package me.ialistannen.livingparchment.feature.add

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_book_add.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.response.BookAddStatus
import me.ialistannen.livingparchment.feature.isbninput.IsbnScanActivity
import javax.inject.Inject

class BookAddActivity : IsbnScanActivity(), AddScreenContract.View {

    @Inject
    override lateinit var presenter: AddScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)

        setSupportActionBar(actionbar as Toolbar)

        lookup_button.setOnClickListener {
            presenter.add(isbn_input_field.text.toString())
        }

        supportActionBar?.let {
            it.title = getString(R.string.activity_add_book_title)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onIsbnScanned(isbn: String) {
        presenter.add(isbn)
    }

    override fun displayAddResult(bookAddStatus: BookAddStatus) {
        val message = when (bookAddStatus) {
            BookAddStatus.ADDED,
            BookAddStatus.ALREADY_CONTAINED -> getString(R.string.activity_add_book_added)
            BookAddStatus.NOT_FOUND -> getString(R.string.status_book_not_found)
            BookAddStatus.INTERNAL_ERROR -> getString(R.string.status_internal_server_error)
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun displayAddFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun setInputIsbn(isbn: String) {
        isbn_input_field.setText(isbn)
    }
}
