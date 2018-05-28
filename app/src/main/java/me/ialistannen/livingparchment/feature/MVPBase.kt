package me.ialistannen.livingparchment.feature

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import dagger.android.DaggerFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.experimental.Job
import me.ialistannen.livingparchment.util.CanDisplayMessage

interface Presenter {

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
abstract class BaseActivity : DaggerAppCompatActivity(), CoroutineHolder, CanDisplayMessage {

    override val job: Job = Job()

    /**
     * The presenter this activity uses.
     */
    protected abstract val presenter: Presenter

    override fun getMessageContext(): Context = this

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        job.cancel()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return false
        }
        return super.onNavigateUp()
    }
}

/**
 * The base activity that handles presenters.
 */
abstract class BaseFragment : DaggerFragment(), CoroutineHolder, CanDisplayMessage {

    override val job: Job = Job()

    /**
     * The presenter this activity uses.
     */
    protected abstract val presenter: Presenter

    override fun getMessageContext(): Context = activity

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()

        retainInstance = true
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        job.cancel()
    }
}

/**
 * A base skeleton class for the [Presenter] able to manage coroutines.
 */
abstract class BasePresenter : Presenter, CoroutineHolder {

    override val job: Job = Job()

    override fun onCreate() {
    }

    @CallSuper
    override fun onDestroy() {
        job.cancel()
    }
}