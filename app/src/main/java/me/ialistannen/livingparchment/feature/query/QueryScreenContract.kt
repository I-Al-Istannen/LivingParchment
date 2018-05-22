package me.ialistannen.livingparchment.feature.query

import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseView

interface QueryScreenContract {

    interface View : BaseView {

        /**
         * Displays the detail page for a given book.
         *
         * @param book the book to return it for
         */
        fun displayDetailPage(book: Book)

        /**
         * Displays the results oi a query.
         *
         * @param books the books returned
         */
        fun displayResults(books: List<Book>)

        /**
         * Displays a generic error message.
         *
         * @param message the error message
         */
        fun displayGenericError(message: String)
    }

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter

}