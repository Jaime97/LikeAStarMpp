package com.jaa.library.domain.dataSource.service

import com.jaa.library.domain.dataSource.service.api.FilmImageApi
import com.jaa.library.domain.entity.FilmImageData

class FilmImageService internal constructor(
    val filmApi: FilmImageApi
)  {

    suspend fun getFilm(title:String) : FilmImageData {
        return filmApi.getFilm(title)
    }

}