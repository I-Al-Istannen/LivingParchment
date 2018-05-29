package me.ialistannen.livingparchment.util

import android.content.Context
import android.util.TypedValue


fun Int.toPixels(context: Context, unit: Int = TypedValue.COMPLEX_UNIT_DIP): Int {
    return TypedValue.applyDimension(unit, this.toFloat(), context.resources.displayMetrics).toInt()
}

fun Float.toPixels(context: Context, unit: Int = TypedValue.COMPLEX_UNIT_DIP): Int {
    return TypedValue.applyDimension(unit, this, context.resources.displayMetrics).toInt()
}