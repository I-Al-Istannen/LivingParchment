package me.ialistannen.livingparchment.feature.query

import me.ialistannen.livingparchment.common.model.Book

interface QueryNavigator {

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
}