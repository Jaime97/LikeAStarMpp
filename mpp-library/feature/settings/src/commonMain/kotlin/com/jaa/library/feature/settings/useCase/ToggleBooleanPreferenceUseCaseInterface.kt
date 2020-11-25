package com.jaa.library.feature.settings.useCase

interface ToggleBooleanPreferenceUseCaseInterface {

    interface ToggleBooleanPreferenceModelListener {
        fun onSuccess()
    }

    suspend fun execute(key:String, listener:ToggleBooleanPreferenceModelListener)

}