package com.jaa.library.domain.dataSource.service.filmImageService

import com.jaa.library.domain.entity.FilmImageData

interface FilmImageServiceInterface {

    suspend fun getFilm(title:String) : FilmImageData

}