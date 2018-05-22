package me.ialistannen.livingparchment.feature.query.list

import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BasePresenter
import javax.inject.Inject

class BookListPresenter @Inject constructor(
        private val view: BookListFragmentContract.View
) : BasePresenter(), BookListFragmentContract.Presenter {

    override fun bookSelected(book: Book) {
        view.displayDetailPage(book)
    }
}