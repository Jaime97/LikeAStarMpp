package com.jaa.library.domain.repository.filmDetail

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorageInterface
import com.jaa.library.domain.dataSource.service.filmImageService.FilmImageServiceInterface
import com.jaa.library.domain.dataSource.storage.FilmDatabaseInterface
import com.jaa.library.domain.useCases.GetFilmImageUseCase

class FilmDetailRepository(
    val filmService: FilmImageServiceInterface,
    override val filmDatabase: FilmDatabaseInterface,
    override val filmMemoryStorage: FilmMemoryStorageInterface
) : FilmDetailRepositoryInterface {

    override suspend fun getImageUrlOfFilm(title:String, listener: GetFilmImageUseCase.GetFilmImageListener) {
        try {
            val filmData = filmService.getFilm(title)
            if (filmData.response.toBoolean() && !filmData.poster.isNullOrEmpty()) {
                listener.onSuccess(filmData.poster)
            } else {
                listener.onError(null)
            }
        } catch (e:Exception) {
            listener.onError(e)
        }
    }

}