package me.ialistannen.livingparchment.feature.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    }

    override fun displayProperties(properties: List<EditableProperty>) {
        edit_book_list.setItems(properties)
    }

    override fun displayPatchStatus(status: BookPatchStatus) {
        when (status) {
            BookPatchStatus.PATCHED -> displayGenericError("Book patched!")
            BookPatchStatus.INTERNAL_ERROR -> displayGenericError("An internal error occurred")
            BookPatchStatus.NOT_FOUND -> displayGenericError("Book not found")
        }
    }

    override fun displayGenericError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }
}