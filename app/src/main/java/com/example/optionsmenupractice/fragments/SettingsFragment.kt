package com.example.optionsmenupractice.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.optionsmenupractice.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}