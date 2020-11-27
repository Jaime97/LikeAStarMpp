package com.jaa.library.feature.filmList.useCase

interface GetBooleanPreferenceUseCaseInterface {

    interface GetBooleanPreferenceModelListener {
        fun onSuccess(value: Boolean)
    }

    suspend fun execute(key:String, listener:GetBooleanPreferenceModelListener)

}