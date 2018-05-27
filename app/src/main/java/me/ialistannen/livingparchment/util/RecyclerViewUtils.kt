package me.ialistannen.livingparchment.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Add a spacing decoration that adds the given padding.
 *
 * @param spacing the spacing to add at the top and bottom
 */
fun RecyclerView.addSpacingDecoration(spacing: Int = 20) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top += spacing
            }
            outRect.bottom += spacing
        }
    })
}