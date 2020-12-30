package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.FilmRowData

interface GetNextPageInFilmListUseCaseInterface {
    interface GetNextPageInFilmListModelListener {
        fun onSuccess(films: List<FilmRowData>)
        fun onError(e:Exception)
    }
    suspend fun execute(wifiActive:Boolean, listener:GetNextPageInFilmListModelListener)
}