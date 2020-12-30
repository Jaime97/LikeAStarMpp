package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.filmList.FilmListRepositoryInterface
import dev.icerock.moko.network.generated.models.FilmData

class GetFilmListUseCase(
    private val filmListRepository : FilmListRepositoryInterface
) {

    interface GetFilmListUseCaseListener {
        fun onSuccess(films: List<FilmData>)
    }

    suspend fun execute(listener:GetFilmListUseCaseListener) {
        listener.onSuccess(filmListRepository.getFilmList())
    }
}