package me.ialistannen.livingparchment.feature.location.add

import me.ialistannen.livingparchment.common.api.response.BookLocationAddStatus
import me.ialistannen.livingparchment.common.api.response.BookLocationDeleteStatus
import me.ialistannen.livingparchment.common.model.BookLocation
import me.ialistannen.livingparchment.feature.BaseView

interface ManageBookLocationContract {

    interface View : BaseView {

        /**
         * Displays the given locations.
         *
         * @param locations the locations to display
         */
        fun displayLocations(locations: List<BookLocation>)

        /**
         * Displays the result of deleting a location.
         *
         * @param status the status
         */
        fun displayDeleteStatus(status: BookLocationDeleteStatus)

        /**
         * Displays the result of adding a location.
         *
         * @param status the status
         */
        fun displayAddStatus(status: BookLocationAddStatus)

        /**
         * Displays a message.
         *
         * @param message the message
         */
        fun displayMessage(message: String)

        /**
         * Sets the refresh status (i.e. toggles indicators).
         *
         * @param refreshes whether the layout is refreshing
         */
        fun setRefreshing(refreshes: Boolean)
    }

    interface Presenter : me.ialistannen.livingparchment.feature.Presenter {

        /**
         * Adds a location with the given name and description.
         *
         * @param name the name of the location
         * @param description the description of the location
         */
        fun addLocation(name: String, description: String)

        /**
         * Deletes a location.
         *
         * @param bookLocation the location to delete
         */
        fun deleteLocation(bookLocation: BookLocation)

        /**
         * Refreshes the data.
         */
        fun refresh()
    }
}