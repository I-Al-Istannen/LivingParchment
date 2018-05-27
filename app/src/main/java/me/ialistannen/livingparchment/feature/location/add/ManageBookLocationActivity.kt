package me.ialistannen.livingparchment.feature.location.add

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_manage_book_location.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.response.BookLocationAddStatus
import me.ialistannen.livingparchment.common.api.response.BookLocationDeleteStatus
import me.ialistannen.livingparchment.common.model.BookLocation
import me.ialistannen.livingparchment.feature.BaseActivity
import javax.inject.Inject

class ManageBookLocationActivity : BaseActivity(), ManageBookLocationContract.View {

    @Inject
    override lateinit var presenter: ManageBookLocationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_book_location)

        add_location_button.setOnClickListener {
            val layoutInflater = LayoutInflater.from(this)
            val dialogView = layoutInflater.inflate(
                    R.layout.dialog_add_book_location,
                    findViewById(android.R.id.content),
                    false
            )

            AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
                    .setPositiveButton(R.string.add_location_add_button) { _, _ ->
                        val nameField = dialogView
                                .findViewById<EditText>(R.id.location_name_field)
                        val descriptionField = dialogView
                                .findViewById<EditText>(R.id.location_description_field)

                        presenter.addLocation(
                                nameField.text.toString(),
                                descriptionField.text.toString()
                        )
                    }
                    .show()
        }

        location_list.setContextMenuListener { item, menu, _, _ ->
            menu.add(R.string.activity_manage_book_location_delete)
                    .setOnMenuItemClickListener {
                        presenter.deleteLocation(item)
                        true
                    }
        }
    }

    override fun displayLocations(locations: List<BookLocation>) {
        location_list.setLocations(locations)
    }

    override fun displayDeleteStatus(status: BookLocationDeleteStatus) {
        when (status) {
            BookLocationDeleteStatus.DELETED -> displayGenericError("Deleted location!")
            BookLocationDeleteStatus.INTERNAL_ERROR -> displayGenericError("Internal server error")
        }
    }

    override fun displayAddStatus(status: BookLocationAddStatus) {
        when (status) {
            BookLocationAddStatus.ADDED -> displayGenericError("Added location!")
            BookLocationAddStatus.INTERNAL_ERROR -> displayGenericError("Internal server error")
        }
    }

    override fun displayGenericError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}