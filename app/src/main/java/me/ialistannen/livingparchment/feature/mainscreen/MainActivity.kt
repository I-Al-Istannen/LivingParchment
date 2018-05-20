package me.ialistannen.livingparchment.feature.mainscreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.feature.BaseActivity
import me.ialistannen.livingparchment.feature.BasePresenter

class MainActivity : BaseActivity(), MainScreenContract.View {

    override val presenter: BasePresenter = MainScreenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(actionbar as Toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shared_action_bar, menu)
        return true
    }
}
