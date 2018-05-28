package me.ialistannen.livingparchment.feature.edit

import me.ialistannen.livingparchment.common.api.response.BookPatchStatus
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseView

interface EditScreenContract {

    interface View : BaseView {

        /**
         * Displays all provided properties, probably the ones from a book.
         *
         * @param properties the properties to display
         */
        fun displayProperties(properties: List<EditableProperty>)

        /**
         * Displays the patch status returned by the server.
         *
         * @param status the status
         */
        fun displayPatchStatus(status: BookPatchStatus)

        /**
         * Displays a message.
         *
         * @param message the message
         */
        fun displayMessage(message: String)
    }

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter {

        /**
         * Sets the displayed book.
         *
         * @param book the displayed book
         */
        fun setBook(book: Book)

        /**
         * Commits the changed book.
         */
        fun commit()
    }
}