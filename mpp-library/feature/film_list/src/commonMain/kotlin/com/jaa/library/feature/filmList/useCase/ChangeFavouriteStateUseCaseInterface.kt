package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.Film

interface ChangeFavouriteStateUseCaseInterface {

    interface ChangeFavouriteStateModelListener {
        fun onSuccess(filmsUpdated:List<Film>)
    }

    suspend fun execute(position:Int, listener:ChangeFavouriteStateModelListener)

}