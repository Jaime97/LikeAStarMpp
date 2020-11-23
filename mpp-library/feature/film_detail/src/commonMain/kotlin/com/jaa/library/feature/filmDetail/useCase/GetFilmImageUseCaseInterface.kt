package com.jaa.library.feature.filmDetail.useCase

interface GetFilmImageUseCaseInterface {

    interface GetFilmImageModelListener {
        fun onSuccess(imageUrl: String)
        fun onError()
    }

    suspend fun execute(title:String, listener:GetFilmImageModelListener)

}