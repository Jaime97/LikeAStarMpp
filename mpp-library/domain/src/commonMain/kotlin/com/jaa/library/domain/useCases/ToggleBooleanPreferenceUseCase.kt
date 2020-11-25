package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.SettingsRepository

class ToggleBooleanPreferenceUseCase(
    private val settingsRepository: SettingsRepository
) {

    interface ToggleBooleanPreferenceListener {
        fun onSuccess()
    }

    suspend fun execute(key:String, listener:ToggleBooleanPreferenceListener) {
        settingsRepository.saveBooleanPreference(key, !settingsRepository.getBooleanPreference(key))
        listener.onSuccess()
    }

}