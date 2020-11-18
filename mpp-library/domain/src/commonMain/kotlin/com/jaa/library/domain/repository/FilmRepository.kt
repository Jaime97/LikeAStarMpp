package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorage
import com.jaa.library.domain.dataSource.service.FilmService
import com.jaa.library.domain.dataSource.storage.FilmDatabase
import dev.icerock.moko.network.generated.models.FilmData

interface FilmRepository {

    val filmService: FilmService
    val filmDatabase: FilmDatabase
    val filmMemoryStorage: FilmMemoryStorage

    fun updateFilm(film: FilmData) {
        synchronizeLocalDataSources()
        filmMemoryStorage.updateFilm(film)
        filmDatabase.updateFilm(film)
    }

    fun synchronizeLocalDataSources() {
        if(filmMemoryStorage.getFilmList().isEmpty()) {
            filmMemoryStorage.saveFilmList(filmDatabase.getFilmList())
        }
    }

    fun getFilm(title:String):FilmData {
        return filmMemoryStorage.getFilmList().first { it.title == title }
    }

}