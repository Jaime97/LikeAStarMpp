package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorageInterface
import com.jaa.library.domain.dataSource.storage.FilmDatabaseInterface
import dev.icerock.moko.network.generated.models.FilmData

interface LocalDatabaseManagerRepository {
    val filmDatabase: FilmDatabaseInterface
    val filmMemoryStorage: FilmMemoryStorageInterface

    fun updateFilm(film: FilmData) {
        synchronizeLocalDataSources()
        filmMemoryStorage.updateFilm(film)
        filmDatabase.updateFilm(film)
    }

    fun synchronizeLocalDataSources() {
        filmMemoryStorage.saveFilmList(filmDatabase.getFilmListWithLocalOffset(0, filmMemoryStorage.getFilmList().size))
    }

    fun getFilm(title:String): FilmData? {
        return try {filmMemoryStorage.getFilmList().first { it.title == title }} catch (e:NoSuchElementException) { null }
    }

}