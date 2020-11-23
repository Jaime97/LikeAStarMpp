package com.jaa.library.feature.filmDetail.useCase

import com.jaa.library.feature.filmDetail.model.FilmDetail

interface GetFilmDetailUseCaseInterface {
    interface GetFilmDetailModelListener {
        fun onSuccess(film:FilmDetail)
    }

    suspend fun execute(title:String, listener:GetFilmDetailModelListener)
}