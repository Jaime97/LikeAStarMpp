package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.Film

interface GetNextPageInFilmListUseCaseInterface {
    interface GetNextPageInFilmListModelListener {
        fun onSuccess(films: List<Film>)
    }
    suspend fun execute(listener:GetNextPageInFilmListModelListener)
}