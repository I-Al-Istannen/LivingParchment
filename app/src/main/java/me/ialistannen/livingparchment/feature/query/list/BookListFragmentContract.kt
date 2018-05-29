package me.ialistannen.livingparchment.feature.query.list

import me.ialistannen.livingparchment.common.api.query.QueryType
import me.ialistannen.livingparchment.common.api.response.BookDeleteStatus
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseView

interface BookListFragmentContract {

    interface View : BaseView {

        /**
         * Opens the detail page for the given book.
         *
         * @param book the book to open it for
         */
        fun displayDetailPage(book: Book)

        /**
         * Display a message.
         *
         * @param message the message to display
         */
        fun displayMessage(message: String)

        /**
         * Displays the delete status.
         *
         * @param status the delete status
         */
        fun displayDeleteStatus(status: BookDeleteStatus)

        /**
         * Displays the books in the GUI.
         *
         * @param books the books to display
         */
        fun displayBooks(books: List<Book>)

        /**
         * Sets whether the screen is currently refreshing.
         *
         * @param refreshing true if it is refreshing
         */
        fun setRefreshIndicator(refreshing: Boolean)
    }

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter {

        /**
         * Called when a book was selected.
         *
         * @param book the book
         */
        fun bookSelected(book: Book)

        /**
         * Deletes the book.
         *
         * @param book the book to delete
         */
        fun deleteBook(book: Book)

        /**
         * Sets the books to display.
         *
         * @param books the books
         */
        fun setBooks(books: List<Book>?)

        /**
         * Called when the view is fully initialized.
         */
        fun onViewCreated()

        /**
         * Called when the user requests a refresh.
         */
        fun onRefreshRequested()

        /**
         * Set the query to use when refreshing or when the cache was GCed-
         *
         * @param queryType the type of the query
         * @param attribute the attribute to search for
         * @param query the query to use
         */
        fun setQuery(queryType: QueryType, attribute: String, query: String)
    }
}