package me.ialistannen.livingparchment.feature.query.detail

import me.ialistannen.livingparchment.feature.BasePresenter
import javax.inject.Inject

class BookDetailPresenter @Inject constructor(
        private val view: BookDetailFragmentContract.View
) : BasePresenter(), BookDetailFragmentContract.Presenter