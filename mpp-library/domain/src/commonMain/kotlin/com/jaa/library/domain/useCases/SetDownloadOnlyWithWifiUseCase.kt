package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.filmList.FilmListRepositoryInterface

class SetDownloadOnlyWithWifiUseCase (
    private val filmListRepository : FilmListRepositoryInterface
) {
    interface SetDownloadOnlyWithWifiListener {
        fun onSuccess()
    }

    suspend fun execute(active:Boolean, listener:SetDownloadOnlyWithWifiListener) {
        filmListRepository.setDownloadOnlyWithWifi(active)
        listener.onSuccess()
    }
}