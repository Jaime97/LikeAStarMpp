
package com.jaa.library.domain.repository.filmList

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorageInterface
import com.jaa.library.domain.dataSource.service.filmService.FilmServiceInterface
import com.jaa.library.domain.dataSource.storage.FilmDatabaseInterface
import com.jaa.library.domain.preferences.PreferenceManagerInterface
import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepository(
    val filmService: FilmServiceInterface,
    override val filmDatabase: FilmDatabaseInterface,
    override val filmMemoryStorage: FilmMemoryStorageInterface,
    override val preferenceManager: PreferenceManagerInterface
) : FilmListRepositoryInterface {

    private var favouriteFilter:Boolean = false
    private var titleFilter:String = ""
    private var downloadOnlyWithWifi = false

    override fun getFilmList():List<FilmData> {
        synchronizeLocalDataSources()
        return filterCurrentListWithFavouriteAndTitle()
    }

    // Returns the list with the new page if it was not empty, an empty list otherwise
    override suspend fun getFilmListWithPage(offset:Int, limit:Int, order:String, wifiActive:Boolean, listener: FilmListRepositoryInterface.OnGetListListener){
        try {
            if(synchronizePagesInDataSources(offset, limit, order, wifiActive)) {
                listener.onSuccess(filterCurrentListWithFavouriteAndTitle())
            } else {
                listener.onSuccess(emptyList())
            }

        } catch (e:Exception) {
            listener.onError(e)
        }
    }

    override fun changeFavouriteFilterState(filter:Boolean) {
        favouriteFilter = filter
    }

    override fun changeTitleFilter(filter:String) {
        titleFilter = filter
    }

    override fun setDownloadOnlyWithWifi(active:Boolean) {
        downloadOnlyWithWifi = active
    }

    private fun filterCurrentListWithFavouriteAndTitle():List<FilmData> {
        return if(favouriteFilter)filterFilmsWithNoLocation(filmMemoryStorage.getFilmList()).filter { it.favourite == true && if(titleFilter != "")it.title.contains(titleFilter, true)else true }
        else filterFilmsWithNoLocation(filmMemoryStorage.getFilmList()).filter { if(titleFilter != "")it.title.contains(titleFilter, true)else true }
    }

    private suspend fun synchronizePagesInDataSources(offset:Int, limit:Int, order:String, wifiActive: Boolean):Boolean {
        var films = filmMemoryStorage.getFilmListWithOffset(offset)
        if(films.isEmpty())  {
            films = filmDatabase.getFilmListWithOnlineOffset(offset, limit)
            // Check download only with wifi preference
            if(films.isEmpty() && (wifiActive || (!wifiActive && !downloadOnlyWithWifi))) {
                //TODO: COMPLETE LAST FILM
                films = groupFilmsByTitle(filmService.getFilmListWithOffset(offset, limit, order))
                filmDatabase.saveFilmList(films)
            }
            filmMemoryStorage.saveFilmList(films)
        }
        return films.isNotEmpty()
    }

    private fun filterFilmsWithNoLocation(films: List<FilmData>):List<FilmData> {
        return films.filter { it.locations != null }
    }

    private fun groupFilmsByTitle(films: List<FilmData>):List<FilmData> {
        return films.groupBy { it.title.replace("\\s".toRegex(), "") }.map {
            it.value.reduce { acc, filmData -> FilmData(acc.title, acc.releaseYear, acc.locations + ";" + filmData.locations,
                acc.funFacts, acc.productionCompany, acc.distributor, acc.director, acc.writer, acc.actor1, acc.actor2, acc.actor3,
                visited = false, favourite = false, numberOfLocations = (filmData.numberOfLocations?:1 + (acc.numberOfLocations?:1)))
            }
        }.sortedBy { it.title }
    }

}
