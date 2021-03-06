package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.filmList.FilmListRepositoryInterface
import dev.icerock.moko.network.generated.models.FilmData

class FilterByFavouriteUseCase (
    private val filmListRepository : FilmListRepositoryInterface
) {

    interface FilterByFavouriteListener {
        fun onSuccess(filmList:List<FilmData>)
    }

    suspend fun execute(filter:Boolean, listener:FilterByFavouriteListener) {
        filmListRepository.changeFavouriteFilterState(filter)
        listener.onSuccess(filmListRepository.getFilmList())
    }

}