package me.ialistannen.livingparchment.feature.edit

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_book_edit.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.response.BookPatchStatus
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.common.serialization.toJson
import me.ialistannen.livingparchment.feature.BaseFragment
import javax.inject.Inject

class EditScreenFragment : BaseFragment(), EditScreenContract.View {

    companion object {
        /**
         * Creates a fragment for the passed book.
         *
         * @param book the book to edit
         * @return a fragment displaying that book
         */
        @JvmStatic
        fun forBook(book: Book): EditScreenFragment {
            return EditScreenFragment().apply {
                arguments = Bundle().apply {
                    putString("book", book.toJson())
                }
            }
        }
    }

    @Inject
    override lateinit var presenter: EditScreenContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_book_edit, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        if (arguments.containsKey("book")) {
            presenter.setBook(arguments.getString("book").fromJson())
        }

        edit_book_list.editable = true

        accept_edit_button.setOnClickListener {
            edit_book_list.commit()
            presenter.commit()
        }

        add_location_button.setOnClickListener {
            presenter.addLocationRequested()
        }
    }

    override fun displayProperties(properties: List<EditableProperty>) {
        edit_book_list.setItems(properties)
    }

    override fun displayPatchStatus(status: BookPatchStatus) {
        when (status) {
            BookPatchStatus.PATCHED -> displayMessage("Book patched!")
            BookPatchStatus.INTERNAL_ERROR -> displayMessage("An internal error occurred")
            BookPatchStatus.NOT_FOUND -> displayMessage("Book not found")
        }
    }

    override fun displayLocations(bookLocations: List<String>) {
        val locationNames: MutableList<String> = mutableListOf()
        locationNames.add(getString(R.string.fragment_book_detail_edit_delete_location))
        locationNames.addAll(bookLocations)

        val dialogView = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_edit_book_select_location, view as ViewGroup, false)
        val locationSpinner = dialogView.findViewById<Spinner>(R.id.location_spinner)
        locationSpinner.adapter = ArrayAdapter(
                activity,
                android.R.layout.simple_spinner_item,
                locationNames
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        AlertDialog.Builder(activity)
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, { _, _ ->
                    val location = locationSpinner.selectedItem as String

                    if (location == getString(R.string.fragment_book_detail_edit_delete_location)) {
                        presenter.setLocation(null)
                    } else {
                        presenter.setLocation(location)
                    }
                })
                .setNegativeButton(android.R.string.cancel, { _, _ -> })
                .show()
    }
}