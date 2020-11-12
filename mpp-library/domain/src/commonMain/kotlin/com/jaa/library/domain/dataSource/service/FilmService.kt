package com.jaa.library.domain.dataSource.service

import com.jaa.library.domain.dataSource.FilmDataSource
import dev.icerock.moko.network.generated.apis.FilmApi
import dev.icerock.moko.network.generated.models.FilmData

class FilmService internal constructor(
    private val filmApi: FilmApi
) : FilmDataSource {

    override suspend fun getFilmList():List<FilmData> {
        return filmApi.resourceWwmuGmzcJsonGet()
    }

    override suspend fun saveFilmList(films: List<FilmData>) {
        print("No update needed in service")
    }

    override suspend fun updateFilm(film: FilmData) {
        print("No update needed in service")
    }

}