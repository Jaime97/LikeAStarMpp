package com.jaa.library.domain.dataSource

import dev.icerock.moko.network.generated.models.FilmData

interface FilmDataSource  {

    suspend fun getFilmList():List<FilmData>
    suspend fun saveFilmList(films: List<FilmData>)
    suspend fun updateFilm(film:FilmData)

}