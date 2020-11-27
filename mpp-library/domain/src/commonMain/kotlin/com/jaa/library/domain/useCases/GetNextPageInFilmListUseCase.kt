package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.FilmListRepository
import com.jaa.library.domain.repository.FilmListRepository.Companion.DATA_SOURCE_ROW_LIMIT
import com.jaa.library.domain.repository.FilmListRepository.Companion.DATA_SOURCE_ROW_TITLE
import dev.icerock.moko.network.generated.models.FilmData

class GetNextPageInFilmListUseCase(
    private val filmListRepository : FilmListRepository
) {
    
    private var offset = 0
    private var gettingList = false

    interface GetNextPageInFilmListListener {
        fun onSuccess(films: List<FilmData>)
    }

    suspend fun execute(wifiActive:Boolean, listener:GetNextPageInFilmListListener) {
        if (!gettingList) {
            gettingList = true
            val filmList = filmListRepository.getFilmListWithPage(offset, DATA_SOURCE_ROW_LIMIT, DATA_SOURCE_ROW_TITLE, wifiActive)
            if (filmList.isNotEmpty()) {
                offset += DATA_SOURCE_ROW_LIMIT
                listener.onSuccess(filmList)
            }
            gettingList = false
        }
    }
}