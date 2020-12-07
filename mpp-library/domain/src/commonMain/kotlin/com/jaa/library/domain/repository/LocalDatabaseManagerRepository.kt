package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorage
import com.jaa.library.domain.dataSource.storage.FilmDatabase
import dev.icerock.moko.network.generated.models.FilmData

interface LocalDatabaseManagerRepository {
    val filmDatabase: FilmDatabase
    val filmMemoryStorage: FilmMemoryStorage

    fun updateFilm(film: FilmData) {
        synchronizeLocalDataSources()
        filmMemoryStorage.updateFilm(film)
        filmDatabase.updateFilm(film)
    }

    fun synchronizeLocalDataSources() {
        filmMemoryStorage.saveFilmList(filmDatabase.getFilmListWithLocalOffset(0, filmMemoryStorage.getFilmList().size))
    }

    fun getFilm(title:String): FilmData {
        return filmMemoryStorage.getFilmList().first { it.title == title }
    }

}