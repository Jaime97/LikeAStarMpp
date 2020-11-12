
package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.FilmDataSource
import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepository internal constructor(
    private val filmService: FilmDataSource,
    private val filmDatabase: FilmDataSource,
    private val filmMemoryStorage: FilmDataSource
) {

    private var favouriteFilter:Boolean = false

    internal suspend fun getFilmList():List<FilmData> {
        syncronizeDataSources()
        return if(favouriteFilter)filmMemoryStorage.getFilmList().filter { it.favourite == true } else filmMemoryStorage.getFilmList()
    }

    internal suspend fun updateFilm(film:FilmData) {
        syncronizeDataSources()
        filmMemoryStorage.updateFilm(film)
        filmDatabase.updateFilm(film)
    }

    private suspend fun syncronizeDataSources() {
        var films = filmMemoryStorage.getFilmList()
        if(films.isEmpty())  {
            films = filmDatabase.getFilmList().sortedBy { it.title }
            if(films.isEmpty()) {
                films = groupFilmsByTitle(filmService.getFilmList()).sortedBy { it.title }
                filmDatabase.saveFilmList(films)
            }
            filmMemoryStorage.saveFilmList(films)
        }
    }

    internal fun changeFavouriteFilterState(filter:Boolean) {
        favouriteFilter = filter
    }

    private fun groupFilmsByTitle(films: List<FilmData>):List<FilmData> {
        return films.groupBy { it.title }.map {
            it.value.reduce { acc, filmData -> FilmData(acc.title, acc.releaseYear, acc.locations + "," + filmData.locations,
                acc.funFacts, acc.productionCompany, acc.distributor, acc.director, acc.writer, acc.actor1, acc.actor2, acc.actor3) }
        }
    }

}
