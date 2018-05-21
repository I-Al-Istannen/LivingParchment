package me.ialistannen.livingparchment.feature.delete

import me.ialistannen.livingparchment.common.api.response.BookDeleteStatus
import me.ialistannen.livingparchment.feature.BaseView

interface DeleteScreenContract {

    interface View : BaseView {

        /**
         * Shows a generic error message.
         *
         * @param error the error message to display
         */
        fun showGenericError(error: String)

        /**
         * Shows the status of the delete request.
         *
         * @param bookDeleteStatus the status
         */
        fun showStatus(bookDeleteStatus: BookDeleteStatus)

        /**
         * Sets the text to display in the ISBN input field.
         *
         * @param text the text to display
         */
        fun setIsbnInputText(text: String)
    }

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter {

        /**
         * Deletes the book with the given ISBN.
         *
         * @param isbn the isbn of the book to delete
         */
        fun delete(isbn: String)
    }
}