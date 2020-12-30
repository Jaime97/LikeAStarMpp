package com.jaa.library.domain.dataSource.memory

import dev.icerock.moko.network.generated.models.FilmData

class FilmMemoryStorageMock : FilmMemoryStorageInterface {
    override fun getFilmList(): List<FilmData> {
        return emptyList()
    }

    override fun getFilmListWithOffset(offset: Int): List<FilmData> {
        return emptyList()
    }

    override fun saveFilmList(films: List<FilmData>) {

    }

    override fun updateFilm(film: FilmData) {

    }
}