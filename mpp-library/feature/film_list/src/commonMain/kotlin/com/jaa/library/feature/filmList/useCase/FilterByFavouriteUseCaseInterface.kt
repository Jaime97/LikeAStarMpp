package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.FilmRowData

interface FilterByFavouriteUseCaseInterface {
    interface FilterByFavouriteModelListener {
        fun onSuccess(filmList:List<FilmRowData>)
    }

    suspend fun execute(filter:Boolean, listener:FilterByFavouriteModelListener)
}