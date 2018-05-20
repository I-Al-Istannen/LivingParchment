package me.ialistannen.livingparchment.feature.add

import me.ialistannen.livingparchment.feature.BasePresenter
import me.ialistannen.livingparchment.feature.BaseView

interface AddScreenContract {

    interface View : BaseView {

        /**
         * Adding was successful
         */
        fun displayAddSuccessful()

        /**
         * Adding was not successful
         *
         * @param message a message explaining the error
         */
        fun displayAddFailed(message: String)
    }

    interface Presenter : BasePresenter {

        /**
         * Adds an ISBN to the library.
         */
        fun add(isbn: String)
    }
}