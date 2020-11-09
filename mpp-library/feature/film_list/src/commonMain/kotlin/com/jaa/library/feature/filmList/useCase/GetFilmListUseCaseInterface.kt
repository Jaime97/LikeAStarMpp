package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.Film

interface GetFilmListUseCaseInterface {
    interface GetFilmListModelListener {
        fun onSuccess(films: List<Film>)
    }
    suspend fun execute(listener:GetFilmListModelListener)
}