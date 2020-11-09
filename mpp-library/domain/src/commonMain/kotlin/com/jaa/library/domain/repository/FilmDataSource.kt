package com.jaa.library.domain.repository

import dev.icerock.moko.network.generated.models.FilmData

interface FilmDataSource  {

    suspend fun getFilmList():List<FilmData>
    suspend fun saveFilmList(films: List<FilmData>)

}