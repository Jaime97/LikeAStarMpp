
package com.jaa.library.domain.repository

import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepository internal constructor(
    private val filmService: FilmDataSource,
    private val filmDatabase: FilmDataSource
) {

    internal suspend fun getFilmList():List<FilmData> {
        val databaseFilms = filmDatabase.getFilmList()
        return if(databaseFilms.isNotEmpty()) databaseFilms else downloadFilmList()
    }

    private suspend fun downloadFilmList():List<FilmData> {
        val films = groupFilmsByTitle(filmService.getFilmList())
        if(films.isNotEmpty()) {
            filmDatabase.saveFilmList(films)
        }
        return films
    }

    private fun groupFilmsByTitle(films: List<FilmData>):List<FilmData> {
        return films.groupBy { it.title }.map {
            it.value.reduce { acc, filmData -> FilmData(acc.title, acc.releaseYear, acc.locations + "," + filmData.locations,
                acc.funFacts, acc.productionCompany, acc.distributor, acc.director, acc.writer, acc.actor1, acc.actor2, acc.actor3) }
        }
    }

}
