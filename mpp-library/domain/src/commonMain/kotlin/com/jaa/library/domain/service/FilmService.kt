package com.jaa.library.domain.service

import com.jaa.library.domain.repository.FilmDataSource
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

}