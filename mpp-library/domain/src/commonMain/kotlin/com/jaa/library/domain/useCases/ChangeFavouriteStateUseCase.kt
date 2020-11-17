package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.FilmListRepository
import dev.icerock.moko.network.generated.models.FilmData

class ChangeFavouriteStateUseCase (
    private val filmListRepository : FilmListRepository
) {

    interface ChangeFavouriteStateListener {
        fun onSuccess(filmsUpdated:List<FilmData>)
    }

    suspend fun execute(position:Int, listener:ChangeFavouriteStateListener) {
        val filmList = filmListRepository.getFilmList()
        val film = filmList[position]
        val favouriteValue:Boolean = film.favourite?:false
        filmListRepository.updateFilm(FilmData(film.title, film.releaseYear, film.locations, film.funFacts,
            film.productionCompany, film.distributor, film.director, film.writer, film.actor1, film.actor2, film.actor3, film.visited, !favouriteValue))
        listener.onSuccess(filmListRepository.getFilmList())
    }

}