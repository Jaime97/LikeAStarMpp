package com.jaa.library.domain.dataSource.storage

import dev.icerock.moko.network.generated.models.FilmData

class FilmDatabaseMock : FilmDatabaseInterface {
    override fun getFilmListWithLocalOffset(offset: Int, limit: Int): List<FilmData> {
        return emptyList()
    }

    override fun getFilmListWithOnlineOffset(offset: Int, limit: Int): List<FilmData> {
        return emptyList()
    }

    override fun saveFilmList(films: List<FilmData>) {

    }

    override fun updateFilm(film: FilmData) {

    }
}