package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.FilmDetailRepository

class GetFilmImageUseCase(
    private val filmDetailRepository : FilmDetailRepository
) {
    interface GetFilmImageListener {
        fun onSuccess(imageUrl: String)
        fun onError()
    }

    suspend fun execute(title:String, listener:GetFilmImageListener) {
        filmDetailRepository.getImageUrlOfFilm(title, object : GetFilmImageListener {
            override fun onSuccess(imageUrl: String) {
                listener.onSuccess(imageUrl)
            }

            override fun onError() {
                listener.onError()
            }

        })
    }
}