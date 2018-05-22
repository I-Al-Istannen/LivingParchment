package me.ialistannen.livingparchment.feature.query.list

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
    }

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter {

        /**
         * Called when a book was selected.
         *
         * @param book the book
         */
        fun bookSelected(book: Book)
    }
}