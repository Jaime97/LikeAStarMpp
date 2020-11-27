package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.FilmListRepository

class SetDownloadOnlyWithWifiUseCase (
    private val filmListRepository : FilmListRepository
) {
    interface SetDownloadOnlyWithWifiListener {
        fun onSuccess()
    }

    suspend fun execute(active:Boolean, listener:SetDownloadOnlyWithWifiListener) {
        filmListRepository.setDownloadOnlyWithWifi(active)
        listener.onSuccess()
    }
}