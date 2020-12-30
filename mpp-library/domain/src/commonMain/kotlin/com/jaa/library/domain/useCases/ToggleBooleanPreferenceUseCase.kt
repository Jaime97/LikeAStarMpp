package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.PreferenceManagerRepository

class ToggleBooleanPreferenceUseCase(
    private val preferenceManagerRepository: PreferenceManagerRepository
) {

    interface ToggleBooleanPreferenceListener {
        fun onSuccess()
    }

    suspend fun execute(key:String, listener:ToggleBooleanPreferenceListener) {
        preferenceManagerRepository.saveBooleanPreference(key, !preferenceManagerRepository.getBooleanPreference(key))
        listener.onSuccess()
    }

}