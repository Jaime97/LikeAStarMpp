package com.jaa.library.domain.preferences

class PreferenceManagerMock : PreferenceManagerInterface {
    override fun putBoolean(key: String, value: Boolean) {

    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return false
    }
}