package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.filmDetail.FilmDetailRepositoryInterface
import dev.icerock.moko.network.generated.models.FilmData

class GetFilmDetailUseCase(
    private val filmDetailRepository : FilmDetailRepositoryInterface
) {
    interface GetFilmDetailListener {
        fun onSuccess(film: FilmData)
    }

    suspend fun execute(title:String, listener:GetFilmDetailListener) {
        listener.onSuccess(filmDetailRepository.getFilm(title))
    }
}