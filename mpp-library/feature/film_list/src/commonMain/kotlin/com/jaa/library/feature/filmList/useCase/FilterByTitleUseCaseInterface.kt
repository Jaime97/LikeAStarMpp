package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.Film

interface FilterByTitleUseCaseInterface {
    interface FilterByTitleModelListener {
        fun onSuccess(filmList:List<Film>)
    }

    suspend fun execute(title:String, listener:FilterByTitleModelListener)
}