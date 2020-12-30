package com.jaa.library.feature.filmList.useCase

import com.jaa.library.feature.filmList.model.FilmRowData

interface FilterByTitleUseCaseInterface {
    interface FilterByTitleModelListener {
        fun onSuccess(filmList:List<FilmRowData>)
    }

    suspend fun execute(title:String, listener:FilterByTitleModelListener)
}