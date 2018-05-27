package me.ialistannen.livingparchment.feature.mainscreen

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.feature.BaseActivity
import me.ialistannen.livingparchment.feature.Presenter
import me.ialistannen.livingparchment.feature.add.BookAddActivity
import me.ialistannen.livingparchment.feature.delete.BookDeleteActivity
import me.ialistannen.livingparchment.feature.location.add.ManageBookLocationActivity
import me.ialistannen.livingparchment.feature.preferences.SettingsActivity
import me.ialistannen.livingparchment.feature.query.BookQueryActivity

class MainActivity : BaseActivity(), MainScreenContract.View {

    override val presenter: Presenter = MainScreenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(actionbar as Toolbar)

        go_to_add_button.setOnClickListener {
            Intent(this, BookAddActivity::class.java).apply {
                startActivity(this)
            }
        }
        go_to_delete_button.setOnClickListener {
            Intent(this, BookDeleteActivity::class.java).apply {
                startActivity(this)
            }
        }
        go_to_query_button.setOnClickListener {
            Intent(this, BookQueryActivity::class.java).apply {
                startActivity(this)
            }
        }
        go_to_location_manage.setOnClickListener {
            Intent(this, ManageBookLocationActivity::class.java).apply {
                startActivity(this)
            }
        }

        supportActionBar?.let {
            it.title = getString(R.string.app_name)
            it.setDisplayShowHomeEnabled(false)
        }

        PreferenceManager.setDefaultValues(this, R.xml.preferences, true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shared_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_item -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
