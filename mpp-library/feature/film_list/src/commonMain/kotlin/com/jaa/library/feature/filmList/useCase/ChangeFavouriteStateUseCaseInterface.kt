package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.FilmRowData

interface ChangeFavouriteStateUseCaseInterface {

    interface ChangeFavouriteStateModelListener {
        fun onSuccess(filmsUpdated:List<FilmRowData>)
        fun onError()
    }

    suspend fun execute(title:String, listener:ChangeFavouriteStateModelListener)

}