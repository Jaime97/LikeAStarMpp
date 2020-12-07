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
        fun onError(e:Exception)
    }

    suspend fun execute(wifiActive:Boolean, listener:GetNextPageInFilmListListener) {
        if (!gettingList) {
            gettingList = true
            filmListRepository.getFilmListWithPage(offset, DATA_SOURCE_ROW_LIMIT, DATA_SOURCE_ROW_TITLE, wifiActive, object : FilmListRepository.OnGetListListener {
                override fun onSuccess(films: List<FilmData>) {
                    if (films.isNotEmpty()) {
                        offset += DATA_SOURCE_ROW_LIMIT
                        listener.onSuccess(films)
                    }
                }

                override fun onError(e:Exception) {
                    listener.onError(e)
                }

            })

            gettingList = false
        }
    }
}