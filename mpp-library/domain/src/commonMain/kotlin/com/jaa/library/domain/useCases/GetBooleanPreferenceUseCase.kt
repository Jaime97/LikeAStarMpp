package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.SettingsRepository

class GetBooleanPreferenceUseCase(
    private val settingsRepository: SettingsRepository
) {

    interface GetBooleanPreferenceListener {
        fun onSuccess(value: Boolean)
    }

    suspend fun execute(key:String, listener:GetBooleanPreferenceListener) {
        listener.onSuccess(settingsRepository.getBooleanPreference(key))
    }

}