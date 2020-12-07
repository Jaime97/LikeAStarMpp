package com.jaa.library.domain.dataSource.service.filmService

import dev.icerock.moko.network.generated.models.FilmData

interface FilmServiceInterface {

    suspend fun getFilmListWithOffset(offset: Int, limit: Int, order: String): List<FilmData>

}