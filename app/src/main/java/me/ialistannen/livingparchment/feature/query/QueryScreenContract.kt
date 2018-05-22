package me.ialistannen.livingparchment.feature.query

import me.ialistannen.livingparchment.common.api.query.QueryType
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

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter {

        /**
         * Executes a query.
         *
         * @param queryType the type of the query
         * @param attribute the attribute to query
         * @param query the query to run
         */
        fun runQuery(queryType: QueryType, attribute: String, query: String)
    }
}