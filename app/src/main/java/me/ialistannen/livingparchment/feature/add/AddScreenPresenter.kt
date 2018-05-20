package me.ialistannen.livingparchment.feature.add

import javax.inject.Inject

class AddScreenPresenter @Inject constructor(
        private val view: AddScreenContract.View
) : AddScreenContract.Presenter {

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun add(isbn: String) {
        view.displayAddSuccessful()
    }
}