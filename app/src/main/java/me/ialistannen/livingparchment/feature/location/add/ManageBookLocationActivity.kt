package me.ialistannen.livingparchment.feature.location.add

import android.app.AlertDialog
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_manage_book_location.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.response.BookLocationAddStatus
import me.ialistannen.livingparchment.common.api.response.BookLocationDeleteStatus
import me.ialistannen.livingparchment.common.api.response.BookLocationPatchStatus
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
            showInfoInputDialog(layoutInflater, R.string.add_location_add_button) { name, description ->
                presenter.addLocation(name, description)
            }
        }

        location_list.setContextMenuListener { item, menu, _, _ ->
            menu.add(R.string.activity_manage_book_location_delete)
                    .setOnMenuItemClickListener {
                        AlertDialog.Builder(this)
                                .setTitle(R.string.activity_manage_book_location_delete_title)
                                .setNegativeButton(android.R.string.cancel, { _, _ -> })
                                .setPositiveButton(R.string.activity_manage_book_location_delete,
                                        { _, _ ->
                                            presenter.deleteLocation(item)
                                        }
                                )
                                .show()
                        true
                    }
            menu.add(R.string.activity_manage_book_location_edit)
                    .setOnMenuItemClickListener {
                        showInfoInputDialog(
                                layoutInflater,
                                R.string.add_location_patch_button,
                                item.name,
                                item.description
                        ) { name, desc ->
                            presenter.patchLocation(item.uuid, name, desc)
                        }
                        true
                    }
        }

        swipe_layout.setOnRefreshListener {
            presenter.refresh()
        }

        @Suppress("DEPRECATION")
        swipe_layout.setColorSchemeColors(
                resources.getColor(R.color.colorPrimary),
                resources.getColor(R.color.colorPrimaryDark),
                resources.getColor(R.color.colorAccent)
        )
    }

    private fun showInfoInputDialog(layoutInflater: LayoutInflater,
                                    @StringRes positiveButtonText: Int,
                                    initialName: String = "",
                                    initialDescription: String = "",
                                    action: (name: String, description: String) -> Unit) {
        val dialogView = layoutInflater.inflate(
                R.layout.dialog_add_book_location,
                findViewById(android.R.id.content),
                false
        )
        val nameField = dialogView
                .findViewById<EditText>(R.id.location_name_field)
        val descriptionField = dialogView
                .findViewById<EditText>(R.id.location_description_field)

        nameField.setText(initialName)
        descriptionField.setText(initialDescription)

        AlertDialog.Builder(this)
                .setView(dialogView)
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .setPositiveButton(positiveButtonText) { _, _ ->
                    action.invoke(
                            nameField.text.toString(),
                            descriptionField.text.toString()
                    )
                }
                .show()
    }

    override fun setRefreshing(refreshes: Boolean) {
        swipe_layout.isRefreshing = refreshes
    }

    override fun displayLocations(locations: List<BookLocation>) {
        location_list.setLocations(locations)
    }

    override fun displayDeleteStatus(status: BookLocationDeleteStatus) {
        when (status) {
            BookLocationDeleteStatus.DELETED -> displayMessage("Deleted location!")
            BookLocationDeleteStatus.INTERNAL_ERROR -> displayMessage("Internal server error")
        }
    }

    override fun displayAddStatus(status: BookLocationAddStatus) {
        when (status) {
            BookLocationAddStatus.ADDED -> displayMessage("Added location!")
            BookLocationAddStatus.INTERNAL_ERROR -> displayMessage("Internal server error")
        }
    }

    override fun displayPatchStatus(status: BookLocationPatchStatus) {
        when (status) {
            BookLocationPatchStatus.PATCHED -> displayMessage("Patched location!")
            BookLocationPatchStatus.NOT_FOUND -> displayMessage("Location not found")
            BookLocationPatchStatus.INTERNAL_ERROR -> displayMessage("Internal server error")
        }
    }
}
