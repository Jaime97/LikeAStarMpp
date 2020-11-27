package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.PreferenceManagerRepository

class GetBooleanPreferenceUseCase(
    private val preferenceManagerRepository: PreferenceManagerRepository
) {

    interface GetBooleanPreferenceListener {
        fun onSuccess(value: Boolean)
    }

    suspend fun execute(key:String, listener:GetBooleanPreferenceListener) {
        listener.onSuccess(preferenceManagerRepository.getBooleanPreference(key))
    }

}