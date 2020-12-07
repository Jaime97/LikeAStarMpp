package com.jaa.library.domain.preferences

import com.russhwolf.settings.Settings

class PreferenceManager(
    private val settings: Settings
) : PreferenceManagerInterface {
    override fun putBoolean(key:String, value:Boolean) {
        settings.putBoolean(key, value)
    }

    override fun getBoolean(key:String, defaultValue:Boolean):Boolean {
        return settings.getBoolean(key, defaultValue)
    }
}