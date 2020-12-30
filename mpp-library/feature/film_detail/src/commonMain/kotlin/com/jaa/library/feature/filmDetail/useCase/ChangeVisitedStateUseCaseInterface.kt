package com.jaa.library.feature.filmDetail.useCase

import com.jaa.library.feature.filmDetail.model.FilmDetail

interface ChangeVisitedStateUseCaseInterface {

    interface ChangeVisitedStateModelListener {
        fun onSuccess(filmUpdated:FilmDetail)
        fun onError()
    }

    suspend fun execute(filmTitle:String, listener:ChangeVisitedStateModelListener)

}