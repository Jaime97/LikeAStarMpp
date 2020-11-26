package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.FilmRowData

interface GetFilmListUseCaseInterface {
    interface GetFilmListUseCaseModelListener {
        fun onSuccess(films: List<FilmRowData>)
    }

    suspend fun execute(listener:GetFilmListUseCaseModelListener)
}