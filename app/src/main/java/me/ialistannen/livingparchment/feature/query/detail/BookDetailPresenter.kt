package me.ialistannen.livingparchment.feature.query.detail

import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BasePresenter
import javax.inject.Inject

class BookDetailPresenter @Inject constructor(
        private val view: BookDetailFragmentContract.View
) : BasePresenter(), BookDetailFragmentContract.Presenter {

    private var book: Book? = null

    override fun setBook(book: Book) {
        this.book = book

        view.displayBook(book)
    }

    override fun editRequested() {
        book?.let { view.displayEditScreen(it) }
    }
}