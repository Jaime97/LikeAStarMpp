package com.jaa.library.domain.repository.filmDetail

import com.jaa.library.domain.repository.LocalDatabaseManagerRepository
import com.jaa.library.domain.useCases.GetFilmImageUseCase

interface FilmDetailRepositoryInterface : LocalDatabaseManagerRepository {

    suspend fun getImageUrlOfFilm(title:String, listener: GetFilmImageUseCase.GetFilmImageListener)

}