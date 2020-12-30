package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.filmDetail.FilmDetailRepositoryInterface

class GetFilmImageUseCase(
    private val filmDetailRepository : FilmDetailRepositoryInterface
) {
    interface GetFilmImageListener {
        fun onSuccess(imageUrl: String)
        fun onError(e: Exception?)
    }

    suspend fun execute(title:String, listener:GetFilmImageListener) {
        filmDetailRepository.getImageUrlOfFilm(title, object : GetFilmImageListener {
            override fun onSuccess(imageUrl: String) {
                listener.onSuccess(imageUrl)
            }

            override fun onError(e: Exception?) {
                listener.onError(e)
            }

        })
    }
}