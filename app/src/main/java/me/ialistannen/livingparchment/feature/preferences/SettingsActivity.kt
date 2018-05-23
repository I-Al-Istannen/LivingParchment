package me.ialistannen.livingparchment.feature.preferences

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_settings.*
import me.ialistannen.livingparchment.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(actionbar as Toolbar)

        fragmentManager.beginTransaction()
                .replace(fragment_container.id, SettingsFragment())
                .commit()

        supportActionBar?.let {
            it.title = getString(R.string.activity_settings_title)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

}
