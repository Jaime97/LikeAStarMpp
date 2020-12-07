package com.jaa.library.domain.dataSource.service.filmService

import dev.icerock.moko.network.generated.apis.FilmApi
import dev.icerock.moko.network.generated.models.FilmData


class FilmService internal constructor(
    val filmApi: FilmApi
) : FilmServiceInterface {

    override suspend fun getFilmListWithOffset(offset: Int, limit: Int, order: String): List<FilmData> {
        return filmApi.resourceWwmuGmzcJsonGet(offset.toString(), limit.toString(), order)
    }

}