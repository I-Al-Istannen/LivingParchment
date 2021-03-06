package me.ialistannen.livingparchment.feature.delete

import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_book_delete.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.response.BookDeleteStatus
import me.ialistannen.livingparchment.feature.isbninput.IsbnScanActivity
import javax.inject.Inject

class BookDeleteActivity : IsbnScanActivity(), DeleteScreenContract.View {

    @Inject
    override lateinit var presenter: DeleteScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_delete)

        setSupportActionBar(actionbar as Toolbar)

        delete_button.setOnClickListener {
            presenter.delete(isbn_input_field.text.toString())
        }

        supportActionBar?.let {
            it.title = getString(R.string.activity_delete_book_title)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onIsbnScanned(isbn: String) {
        presenter.delete(isbn)
    }

    override fun showStatus(bookDeleteStatus: BookDeleteStatus) {
        val message = when (bookDeleteStatus) {
            BookDeleteStatus.DELETED -> getString(R.string.activity_delete_book_deleted)
            BookDeleteStatus.NOT_FOUND -> getString(R.string.status_book_not_found)
            BookDeleteStatus.INTERNAL_ERROR -> getString(R.string.status_internal_server_error)
        }

        displayMessage(message)
    }

    override fun setIsbnInputText(text: String) {
        isbn_input_field.setText(text)
    }
}
