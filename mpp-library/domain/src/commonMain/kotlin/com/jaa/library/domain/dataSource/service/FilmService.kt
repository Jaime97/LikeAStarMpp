package com.jaa.library.domain.dataSource.service

import dev.icerock.moko.network.generated.apis.FilmApi
import dev.icerock.moko.network.generated.models.FilmData


class FilmService internal constructor(
    val filmApi: FilmApi
) {

    suspend fun getFilmListWithOffset(offset: Int, limit: Int, order: String): List<FilmData> {
        return filmApi.resourceWwmuGmzcJsonGet(offset.toString(), limit.toString(), order)
    }

}