package com.jaa.library.feature.settings.useCase

interface GetBooleanPreferenceUseCaseInterface {

    interface GetBooleanPreferenceModelListener {
        fun onSuccess(value: Boolean)
    }

    suspend fun execute(key:String, listener:GetBooleanPreferenceModelListener)

}