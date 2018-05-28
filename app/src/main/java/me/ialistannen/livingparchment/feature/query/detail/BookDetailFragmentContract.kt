package me.ialistannen.livingparchment.feature.query.detail

import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseView

interface BookDetailFragmentContract {

    interface View : BaseView {

        /**
         * Displays the book edit screen.
         *
         * @param book the book to edit
         */
        fun displayEditScreen(book: Book)

        /**
         * Displays the given book.
         *
         * @param book the book to display
         */
        fun displayBook(book: Book)
    }

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter {

        /**
         * Called when the user requests to edit the current book.
         */
        fun editRequested()

        /**
         * Sets the book to display.
         *
         * @param book the book to display
         */
        fun setBook(book: Book)
    }
}