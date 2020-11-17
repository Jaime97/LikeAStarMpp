package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.FilmListRepository
import dev.icerock.moko.network.generated.models.FilmData

class FilterByTitleUseCase(
    private val filmListRepository : FilmListRepository
) {
    interface FilterByTitleListener {
        fun onSuccess(filmList:List<FilmData>)
    }

    suspend fun execute(title:String, listener:FilterByTitleListener) {
        filmListRepository.changeTitleFilter(title)
        listener.onSuccess(filmListRepository.getFilmList())
    }
}