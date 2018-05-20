package me.ialistannen.livingparchment.feature.add

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_book_add.*
import me.ialistannen.livingparchment.R
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
                Toast.makeText(this, "SCAN", Toast.LENGTH_LONG).show()
                true
            }
            else -> false
        }
    }

    override fun displayAddSuccessful() {
        Toast.makeText(this, "Successfully added", Toast.LENGTH_LONG).show()
    }

    override fun displayAddFailed(message: String) {
        Toast.makeText(this, ":(", Toast.LENGTH_LONG).show()
    }
}
