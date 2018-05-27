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
         * Displays a generic error message.
         *
         * @param error the error message to display
         */
        fun displayGenericError(error: String)
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
    }
}