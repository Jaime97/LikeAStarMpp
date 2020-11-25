package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorage
import com.jaa.library.domain.dataSource.service.FilmImageService
import com.jaa.library.domain.dataSource.storage.FilmDatabase
import com.jaa.library.domain.useCases.GetFilmImageUseCase

class FilmDetailRepository(
    val filmService: FilmImageService,
    override val filmDatabase: FilmDatabase,
    override val filmMemoryStorage: FilmMemoryStorage
) : LocalDatabaseRepository {

    suspend fun getImageUrlOfFilm(title:String, listener: GetFilmImageUseCase.GetFilmImageListener) {
        val filmData = filmService.getFilm(title)
        return if(filmData.response.toBoolean() && !filmData.poster.isNullOrEmpty()) listener.onSuccess(filmData.poster) else listener.onError()
    }

}