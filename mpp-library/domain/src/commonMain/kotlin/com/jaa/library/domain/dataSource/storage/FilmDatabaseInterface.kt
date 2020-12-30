package com.jaa.library.domain.dataSource.storage

import dev.icerock.moko.network.generated.models.FilmData

interface FilmDatabaseInterface {

    fun getFilmListWithLocalOffset(offset: Int, limit: Int): List<FilmData>

    fun getFilmListWithOnlineOffset(offset: Int, limit: Int): List<FilmData>

    fun saveFilmList(films: List<FilmData>)

    fun updateFilm(film: FilmData)

}