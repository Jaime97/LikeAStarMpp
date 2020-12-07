package com.jaa.library.domain.repository.settings

import com.jaa.library.domain.preferences.PreferenceManagerInterface

class SettingsRepository(
    override val preferenceManager: PreferenceManagerInterface
) : SettingsRepositoryInterace