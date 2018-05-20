package me.ialistannen.livingparchment.feature.mainscreen

import android.os.Bundle
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.feature.BaseActivity
import me.ialistannen.livingparchment.feature.BasePresenter

class MainActivity : BaseActivity(), MainScreenContract.View {

    override val presenter: BasePresenter = MainScreenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
