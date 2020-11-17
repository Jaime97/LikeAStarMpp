package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.Film

interface FilterByFavouriteUseCaseInterface {
    interface FilterByFavouriteModelListener {
        fun onSuccess(filmList:List<Film>)
    }

    suspend fun execute(filter:Boolean, listener:FilterByFavouriteModelListener)
}