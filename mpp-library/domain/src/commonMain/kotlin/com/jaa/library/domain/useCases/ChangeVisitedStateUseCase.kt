package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.filmDetail.FilmDetailRepositoryInterface
import dev.icerock.moko.network.generated.models.FilmData

class ChangeVisitedStateUseCase (
    private val filmDetailRepository : FilmDetailRepositoryInterface
) {

    interface ChangeVisitedStateListener {
        fun onSuccess(filmUpdated: FilmData)
    }

    suspend fun execute(filmTitle:String, listener:ChangeVisitedStateListener) {
        val film = filmDetailRepository.getFilm(filmTitle)
        val visitedState = film.visited?:false
        filmDetailRepository.updateFilm(
            FilmData(film.title, film.releaseYear, film.locations, film.funFacts,
            film.productionCompany, film.distributor, film.director, film.writer, film.actor1, film.actor2, film.actor3, !visitedState, film.favourite)
        )
        listener.onSuccess(filmDetailRepository.getFilm(filmTitle))
    }
}