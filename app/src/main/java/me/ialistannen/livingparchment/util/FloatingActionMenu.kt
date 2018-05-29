package me.ialistannen.livingparchment.util

import android.content.Context
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.*
import android.widget.LinearLayout
import me.ialistannen.livingparchment.R

class FloatingActionMenu : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val fabOpen = AnimationUtils
            .loadAnimation(context, R.anim.floating_action_menu_open_children)
    private val fabClose = AnimationUtils
            .loadAnimation(context, R.anim.floating_action_menu_open_children).apply {
                interpolator = ReverseInterpolator()
            }
    private val mainFabOpen = AnimationUtils
            .loadAnimation(context, R.anim.floating_action_menu_animate_main_open)
    private val mainFabClose = AnimationUtils
            .loadAnimation(context, R.anim.floating_action_menu_animate_main_open).apply {
                interpolator = ReverseInterpolator()
            }

    private val childrenExceptMainButton
        get() = (0 until (childCount - 1)).map { getChildAt(it) }

    private val mainButton
        get() = getChildAt(childCount - 1)

    var opened: Boolean = false
        private set

    init {
        orientation = VERTICAL
        dividerDrawable = ContextCompat.getDrawable(context, R.drawable.empty_sized_divider)
        showDividers = SHOW_DIVIDER_MIDDLE

        post {
            mainButton.setOnClickListener { setOpen(!opened) }
        }
    }

    override fun onViewAdded(child: View) {
        super.onViewAdded(child)

        childrenExceptMainButton.forEach { it.visibility = View.INVISIBLE }

        mainButton.visibility = View.VISIBLE
    }

    /**
     * Sets whether this menu is opened.
     *
     * @param open true if the menu should be opened
     */
    fun setOpen(open: Boolean) {
        opened = open
        if (open) {
            childrenExceptMainButton.forEach {
                val animationSet = fabOpen as AnimationSet
                it.startAnimation(buildTranslateAnimation(it, animationSet))
            }
            mainButton.startAnimation(mainFabOpen)
        } else {
            childrenExceptMainButton.forEach {
                val animationSet = fabClose as AnimationSet
                it.startAnimation(buildTranslateAnimation(it, animationSet))
            }
            mainButton.startAnimation(mainFabClose)
        }
    }

    private class ReverseInterpolator : Interpolator {
        override fun getInterpolation(input: Float): Float {
            return Math.abs(input - 1)
        }
    }

    private fun buildTranslateAnimation(view: View, base: AnimationSet): AnimationSet {
        val newAnimationSet = AnimationSet(context, null)
        base.animations.forEach { newAnimationSet.addAnimation(it) }
        newAnimationSet.fillAfter = true
        newAnimationSet.interpolator = base.interpolator

        val childBounds = Rect()
        view.getDrawingRect(childBounds)
        offsetDescendantRectToMyCoords(view, childBounds)
        val fromY = height - childBounds.bottom.toFloat()

        val translateAnimation = TranslateAnimation(0f, 0f, fromY, 0f).apply {
            duration = resources.getInteger(R.integer.floating_action_menu_animation_time).toLong()
            interpolator = AccelerateInterpolator()
        }

        newAnimationSet.addAnimation(translateAnimation)

        return newAnimationSet
    }
}