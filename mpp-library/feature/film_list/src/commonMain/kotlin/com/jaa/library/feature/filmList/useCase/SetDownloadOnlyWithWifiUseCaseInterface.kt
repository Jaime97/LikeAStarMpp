package com.jaa.library.feature.filmList.useCase

interface SetDownloadOnlyWithWifiUseCaseInterface {
    interface SetDownloadOnlyWithWifiModelListener {
        fun onSuccess()
    }

    suspend fun execute(active:Boolean, listener:SetDownloadOnlyWithWifiModelListener)
}