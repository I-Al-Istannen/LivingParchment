package me.ialistannen.livingparchment.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Hides the keyboard from the view.
 */
fun View.hideKeyboard() {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    manager.hideSoftInputFromWindow(windowToken, 0)
}