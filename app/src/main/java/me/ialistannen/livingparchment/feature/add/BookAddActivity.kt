package me.ialistannen.livingparchment.feature.add

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_book_add.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.response.BookAddStatus
import me.ialistannen.livingparchment.feature.BaseActivity
import javax.inject.Inject

class BookAddActivity : BaseActivity(), AddScreenContract.View {

    @Inject
    override lateinit var presenter: AddScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)

        setSupportActionBar(actionbar as Toolbar)

        lookup_button.setOnClickListener {
            presenter.add(isbn_input_field.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_add_book_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scan_barcode -> {
                IntentIntegrator(this).initiateScan()
                true
            }
            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                ?: return

        if (!result.contents.isNullOrBlank()) {
            presenter.add(result.contents)
        }
    }

    override fun displayAddResult(bookAddStatus: BookAddStatus) {
        val message = when (bookAddStatus) {
            BookAddStatus.ADDED,
            BookAddStatus.ALREADY_CONTAINED -> getString(R.string.activity_add_book_added)
            BookAddStatus.NOT_FOUND -> getString(R.string.activity_add_book_not_found)
            BookAddStatus.INTERNAL_ERROR -> getString(R.string.activity_add_book_internal_server_error)
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
