package com.jaa.library.domain.repository

import com.jaa.library.domain.preferences.PreferenceManagerInterface

interface PreferenceManagerRepository {
    val preferenceManager: PreferenceManagerInterface

    fun saveBooleanPreference(key:String, value:Boolean) {
        preferenceManager.putBoolean(key, value)
    }

    fun getBooleanPreference(key:String) : Boolean {
        return preferenceManager.getBoolean(key, false)
    }

}