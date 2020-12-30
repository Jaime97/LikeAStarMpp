package com.jaa.library.domain.repository.filmList

import com.jaa.library.domain.repository.LocalDatabaseManagerRepository
import com.jaa.library.domain.repository.PreferenceManagerRepository
import dev.icerock.moko.network.generated.models.FilmData

interface FilmListRepositoryInterface : LocalDatabaseManagerRepository,
    PreferenceManagerRepository {

    companion object {
        const val DATA_SOURCE_ROW_TITLE = "title"
        const val DATA_SOURCE_ROW_LIMIT = 80
    }

    fun getFilmList():List<FilmData>

    suspend fun getFilmListWithPage(offset:Int, limit:Int, order:String, wifiActive:Boolean, listener: OnGetListListener)

    fun changeFavouriteFilterState(filter:Boolean)

    fun changeTitleFilter(filter:String)

    fun setDownloadOnlyWithWifi(active:Boolean)

    interface OnGetListListener {
        fun onSuccess(films: List<FilmData>)
        fun onError(e:Exception)
    }

}