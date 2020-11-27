package com.jaa.library.domain.repository

import com.jaa.library.domain.preferences.PreferenceManager

class SettingsRepository(
    override val preferenceManager: PreferenceManager
) : PreferenceManagerRepository