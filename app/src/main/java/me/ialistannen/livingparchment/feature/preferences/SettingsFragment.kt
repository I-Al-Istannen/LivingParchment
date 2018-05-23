package me.ialistannen.livingparchment.feature.preferences

import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceGroup
import me.ialistannen.livingparchment.R

class SettingsFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.preferences)

        getAllPreferences()
                .filterIsInstance(EditTextPreference::class.java)
                .forEach {
                    it.updateSummary()
                    it.setOnPreferenceChangeListener { preference, newValue ->
                        (preference as EditTextPreference).updateSummary(newValue)

                        true
                    }
                }
    }

    private fun getAllPreferences(root: Preference = preferenceScreen): List<Preference> {
        val result: MutableList<Preference> = arrayListOf()
        if (root is PreferenceGroup) {
            val childCount = root.preferenceCount
            for (i in 0 until childCount) {
                result.addAll(getAllPreferences(root.getPreference(i)))
            }
        } else {
            result.add(root)
        }

        return result
    }

    private fun EditTextPreference.updateSummary(value: Any? = null) {
        if (extras?.containsKey("summary_old") == false) {
            extras.putString("summary_old", summary.toString())
        }
        val summaryBlueprint = extras.getString("summary_old")

        val argument = value
                ?: sharedPreferences.getString(key, "")

        summary = summaryBlueprint.format(argument)
    }
}