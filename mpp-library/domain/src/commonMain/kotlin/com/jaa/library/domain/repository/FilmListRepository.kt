
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
        val groupedFilms = filmService.getFilmList().groupBy { it.title }
        val films = groupedFilms.map {
            it.value.reduce { acc, filmData -> FilmData(acc.title, acc.releaseYear, acc.locations + "," + filmData.locations,
                acc.funFacts, acc.productionCompany, acc.distributor, acc.director, acc.writer, acc.actor1, acc.actor2, acc.actor3) }
        }
        if(films.isNotEmpty()) {
            filmDatabase.saveFilmList(films)
        }
        return films
    }


}
