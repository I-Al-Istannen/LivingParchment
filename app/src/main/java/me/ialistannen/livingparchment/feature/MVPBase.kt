package me.ialistannen.livingparchment.feature

import android.os.Bundle
import android.support.annotation.CallSuper
import dagger.android.support.DaggerAppCompatActivity

interface BasePresenter {

    /**
     *  Basic lifecycle method. Will be called when the corresponding view is created.
     */
    fun onCreate()

    /**
     * Basic lifecycle method. Will be called when the corresponding view is destroyed.
     */
    fun onDestroy()
}

interface BaseView

/**
 * The base activity that handles presenters.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    /**
     * The presenter this activity uses.
     */
    protected abstract val presenter: BasePresenter

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}