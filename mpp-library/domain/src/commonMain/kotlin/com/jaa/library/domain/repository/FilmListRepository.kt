
package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.FilmDataSource
import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepository internal constructor(
    private val filmService: FilmDataSource,
    private val filmDatabase: FilmDataSource,
    private val filmMemoryStorage: FilmDataSource
) {

    internal suspend fun getFilmList():List<FilmData> {
        val filmsInMemory = filmMemoryStorage.getFilmList()
        return if(filmsInMemory.isNotEmpty())  {
            filmsInMemory
        } else {
            val filmsInDatabase = updateFilmListFromDatabase()
            if(filmsInDatabase.isNotEmpty()) filmsInDatabase else updateFilmListFromService()
        }
    }

    private suspend fun updateFilmListFromDatabase():List<FilmData> {
        val films = filmDatabase.getFilmList()
        if(films.isNotEmpty()) {
            filmMemoryStorage.saveFilmList(films)
        }
        return films
    }

    private suspend fun updateFilmListFromService():List<FilmData> {
        val films = groupFilmsByTitle(filmService.getFilmList())
        if(films.isNotEmpty()) {
            filmDatabase.saveFilmList(films)
            filmMemoryStorage.saveFilmList(films)
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
