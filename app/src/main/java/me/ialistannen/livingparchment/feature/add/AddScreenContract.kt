package me.ialistannen.livingparchment.feature.add

import me.ialistannen.livingparchment.common.api.response.BookAddStatus
import me.ialistannen.livingparchment.feature.BaseView

interface AddScreenContract {

    interface View : BaseView {

        /**
         * Adding was not successful
         *
         * @param message a message explaining the error
         */
        fun displayAddFailed(message: String)

        /**
         * A valid response was returned by the server.
         *
         * @param bookAddStatus the status the server returned
         */
        fun displayAddResult(bookAddStatus: BookAddStatus)

        /**
         * Sets the isbn in the input field
         *
         * @param isbn the isbn in the input field
         */
        fun setInputIsbn(isbn: String)
    }

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter {

        /**
         * Adds an ISBN to the library.
         */
        fun add(isbn: String)
    }
}