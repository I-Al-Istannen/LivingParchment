package me.ialistannen.livingparchment.feature.add

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_book_add.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.response.BookAddStatus
import me.ialistannen.livingparchment.common.model.BookLocation
import me.ialistannen.livingparchment.feature.isbninput.IsbnScanActivity
import java.util.*
import javax.inject.Inject

class BookAddActivity : IsbnScanActivity(), AddScreenContract.View {

    @Inject
    override lateinit var presenter: AddScreenContract.Presenter

    private var selectedUuid: UUID? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)

        setSupportActionBar(actionbar as Toolbar)

        lookup_button.setOnClickListener {
            val location = location_spinner.selectedItem as BookLocation?
            presenter.add(isbn_input_field.text.toString(), location)
        }

        supportActionBar?.let {
            it.title = getString(R.string.activity_add_book_title)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }

        if (savedInstanceState != null && savedInstanceState.containsKey("location_id")) {
            selectedUuid = UUID.fromString(savedInstanceState.getString("location_id"))
            selectLocation(selectedUuid!!)
        }
    }

    override fun onIsbnScanned(isbn: String) {
        presenter.add(isbn, location_spinner.selectedItem as BookLocation?)
    }

    override fun displayAddResult(bookAddStatus: BookAddStatus) {
        val message = when (bookAddStatus) {
            BookAddStatus.ADDED,
            BookAddStatus.ALREADY_CONTAINED -> getString(R.string.activity_add_book_added)
            BookAddStatus.NOT_FOUND -> getString(R.string.status_book_not_found)
            BookAddStatus.INTERNAL_ERROR -> getString(R.string.status_internal_server_error)
        }

        showToast(message)
    }

    override fun displayAddFailed(message: String) {
        showToast(message)
    }

    override fun setInputIsbn(isbn: String) {
        isbn_input_field.setText(isbn)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        (location_spinner.selectedItem as BookLocation?)?.let {
            outState.putString("location_id", it.uuid.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun setAvailableLocations(locations: List<BookLocation>) {
        location_spinner.adapter = LocationAdapter(this, locations)

        location_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedUuid = null
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val item = parent.adapter.getItem(position) as BookLocation?

                selectedUuid = item?.uuid
            }
        }

        selectedUuid?.let { selectLocation(it) }
    }

    private fun selectLocation(uuid: UUID) {
        if (location_spinner.adapter == null) {
            return
        }
        for (i in 0 until location_spinner.adapter.count) {
            val bookLocation = location_spinner.adapter.getItem(i) as BookLocation?
            if (bookLocation?.uuid == uuid) {
                location_spinner.setSelection(i)
            }
        }
    }

    private class LocationAdapter(private val context: Context,
                                  items: List<BookLocation>
    ) : BaseAdapter(), SpinnerAdapter {

        private val items: List<BookLocation?> = mutableListOf<BookLocation?>(null)
                .apply { addAll(items) }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val textView: TextView = inflateOrReuse(
                    convertView,
                    parent,
                    android.R.layout.simple_spinner_item
            )

            initTextView(position, textView)

            return textView
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val textView: TextView = inflateOrReuse(
                    convertView,
                    parent,
                    android.R.layout.simple_spinner_dropdown_item
            )

            initTextView(position, textView)

            return textView
        }

        private fun inflateOrReuse(convertView: View?, parent: ViewGroup?,
                                   @LayoutRes layoutRes: Int): TextView {
            return if (convertView == null) {
                LayoutInflater.from(context).inflate(layoutRes, parent, false)
                        as TextView
            } else {
                convertView as TextView
            }
        }

        private fun initTextView(position: Int, textView: TextView) {
            if (position == 0) {
                textView.text = context.getString(R.string.activity_add_book_no_location)
            } else {
                val item = getItem(position)

                textView.text = item!!.name
            }
        }

        override fun getItem(position: Int): BookLocation? = items[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getCount(): Int = items.size
    }
}