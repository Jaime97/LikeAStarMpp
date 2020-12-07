package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorageInterface
import com.jaa.library.domain.dataSource.memory.FilmMemoryStorageMock
import com.jaa.library.domain.dataSource.storage.FilmDatabaseInterface
import com.jaa.library.domain.dataSource.storage.FilmDatabaseMock
import com.jaa.library.domain.preferences.PreferenceManagerInterface
import com.jaa.library.domain.preferences.PreferenceManagerMock
import com.jaa.library.domain.repository.filmList.FilmListRepositoryInterface
import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepositoryMock(
    private val filmList: MutableList<FilmData>
) : FilmListRepositoryInterface {

    override val filmDatabase: FilmDatabaseInterface = FilmDatabaseMock()
    override val filmMemoryStorage: FilmMemoryStorageInterface = FilmMemoryStorageMock()
    override val preferenceManager: PreferenceManagerInterface = PreferenceManagerMock()

    override fun getFilmList(): List<FilmData> {
        return filmList
    }

    override fun getFilm(title: String): FilmData? {
        return try {filmList.first { it.title == title }} catch (e:NoSuchElementException) { null }
    }

    override fun updateFilm(film: FilmData) {
        val index = filmList.indexOf(filmList.find { it.title == film.title })
        filmList[index] = film
    }

    override suspend fun getFilmListWithPage(
        offset: Int,
        limit: Int,
        order: String,
        wifiActive: Boolean,
        listener: FilmListRepositoryInterface.OnGetListListener
    ) {
    }

    override fun changeFavouriteFilterState(filter: Boolean) {
    }

    override fun changeTitleFilter(filter: String) {
    }

    override fun setDownloadOnlyWithWifi(active: Boolean) {
    }
}