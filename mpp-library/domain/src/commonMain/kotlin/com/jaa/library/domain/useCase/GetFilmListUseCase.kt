package com.jaa.library.domain.useCase

import com.jaa.library.domain.repository.FilmListRepository
import dev.icerock.moko.network.generated.models.FilmData

class GetFilmListUseCase(
    private val filmListRepository : FilmListRepository
) {

    interface GetFilmListListener {
        fun onSuccess(films: List<FilmData>)
    }

    suspend fun execute(listener:GetFilmListListener) {
        val films = filmListRepository.getFilmList()
        listener.onSuccess(films)
    }
}