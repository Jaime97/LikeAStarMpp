
package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorage
import com.jaa.library.domain.dataSource.service.FilmService
import com.jaa.library.domain.dataSource.storage.FilmDatabase
import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepository(
    val filmService: FilmService,
    override val filmDatabase: FilmDatabase,
    override val filmMemoryStorage: FilmMemoryStorage
) : FilmRepository {

    companion object {
        const val DATA_SOURCE_ROW_TITLE = "title"
        const val DATA_SOURCE_ROW_LIMIT = 80
    }

    private var favouriteFilter:Boolean = false
    private var titleFilter:String = ""

    internal fun getFilmList():List<FilmData> {
        synchronizeLocalDataSources()
        return filterCurrentList()
    }

    internal suspend fun getFilmListWithPage(offset:Int, limit:Int, order:String):List<FilmData> {
        synchronizePagesInDataSources(offset, limit, order)
        return filterCurrentList()
    }

    internal fun changeFavouriteFilterState(filter:Boolean) {
        favouriteFilter = filter
    }

    internal fun changeTitleFilter(filter:String) {
        titleFilter = filter
    }

    private fun filterCurrentList():List<FilmData> {
        return if(favouriteFilter)filmMemoryStorage.getFilmList().filter { it.favourite == true && if(titleFilter != "")it.title.contains(titleFilter, true)else true }
        else filmMemoryStorage.getFilmList().filter { if(titleFilter != "")it.title.contains(titleFilter, true)else true }
    }

    private suspend fun synchronizePagesInDataSources(offset:Int, limit:Int, order:String) {
        var films = filmMemoryStorage.getFilmListWithOffset(offset)
        if(films.isEmpty())  {
            films = filmDatabase.getFilmListWithOffset(offset, limit)
            if(films.isEmpty()) {
                //TODO: COMPLETE LAST FILM
                films = groupFilmsByTitle(filmService.getFilmListWithOffset(offset, limit, order))
                filmDatabase.saveFilmList(films)
            }
            filmMemoryStorage.saveFilmList(films)
        }
    }

    private fun groupFilmsByTitle(films: List<FilmData>):List<FilmData> {
        return films.groupBy { it.title.replace("\\s".toRegex(), "") }.map {
            it.value.reduce { acc, filmData -> FilmData(acc.title, acc.releaseYear, acc.locations + "," + filmData.locations,
                acc.funFacts, acc.productionCompany, acc.distributor, acc.director, acc.writer, acc.actor1, acc.actor2, acc.actor3) }
        }
    }

}
