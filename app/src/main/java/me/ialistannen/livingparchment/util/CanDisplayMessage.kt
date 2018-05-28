package me.ialistannen.livingparchment.util

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

interface CanDisplayMessage {

    fun getMessageContext(): Context

    /**
     * Displays a message.
     *
     * @param message the message to display
     */
    fun displayMessage(message: String) {
        val view = getViewFromContext()

        if (view != null) {
            showSnackbarMessage(view, message)
        } else {
            Toast.makeText(getMessageContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    private fun getViewFromContext(): View? {
        val context = getMessageContext()
        return when {
            context is Activity -> context.findViewById(android.R.id.content)
            context is Fragment && context.isVisible -> context.view
            else -> null
        }
    }

    /**
     * Shows a snackbar message.
     *
     * @param view the view to pass it. Tries to use the [getMessageContext] by default
     * @param message the message to display
     * @param length the length to show it. [Snackbar.LENGTH_LONG] by default
     * @param modification any modification you want to do. Adds an ok button by default
     */
    fun showSnackbarMessage(view: View? = getViewFromContext(), message: String,
                            length: Int = Snackbar.LENGTH_LONG,
                            modification: (Snackbar) -> Unit = {
                                it.setAction(android.R.string.ok, {})
                            }) {
        view?.let {
            val snackbar = Snackbar.make(it, message, length)
            modification.invoke(snackbar)

            snackbar.show()
        }
    }
}