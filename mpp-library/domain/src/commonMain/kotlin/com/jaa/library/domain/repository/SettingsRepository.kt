package com.jaa.library.domain.repository

import com.jaa.library.domain.preferences.PreferenceManager

class SettingsRepository(
    private val storage: PreferenceManager
) {

    fun saveBooleanPreference(key:String, value:Boolean) {
        storage.putBoolean(key, value)
    }

    fun getBooleanPreference(key:String) : Boolean {
        return storage.getBoolean(key, false)
    }

}