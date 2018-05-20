package me.ialistannen.livingparchment.feature.mainscreen

import android.util.Log

class MainScreenPresenter : MainScreenContract.Presenter {
    override fun onCreate() {
        Log.w("hey", "On create")
    }

    override fun onDestroy() {
        Log.w("hey", "On destroy")
    }
}