package me.ialistannen.livingparchment.feature.query.query

import me.ialistannen.livingparchment.common.api.query.QueryType
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseView

interface QueryFragmentContract {

    interface View : BaseView {

        /**
         * Displays the results of a query.
         *
         * @param books the books returned
         */
        fun displayResults(books: List<Book>)

        /**
         * Displays a message.
         *
         * @param message the error message
         */
        fun displayMessage(message: String)
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