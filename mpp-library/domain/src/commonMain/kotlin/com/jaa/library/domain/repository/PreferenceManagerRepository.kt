package com.jaa.library.domain.repository

import com.jaa.library.domain.preferences.PreferenceManager

interface PreferenceManagerRepository {
    val preferenceManager: PreferenceManager

    fun saveBooleanPreference(key:String, value:Boolean) {
        preferenceManager.putBoolean(key, value)
    }

    fun getBooleanPreference(key:String) : Boolean {
        return preferenceManager.getBoolean(key, false)
    }

}