package com.jaa.library.feature.filmDetail.useCase

interface GetFilmImageUseCaseInterface {

    interface GetFilmImageModelListener {
        fun onSuccess(imageUrl: String)
        fun onError(e: Exception?)
    }

    suspend fun execute(title:String, listener:GetFilmImageModelListener)

}