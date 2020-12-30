package com.jaa.library.feature.filmList.presentation

import com.jaa.library.feature.filmList.model.FilmRowData
import dev.icerock.moko.units.TableUnitItem

interface FilmTableDataFactoryInterface {
    fun createFilmRow(id: Long, film: FilmRowData, listener: ListRowTappedListener): TableUnitItem

    interface ListRowTappedListener {
        fun onRowTapped(title:String)
        fun onFavouriteButtonTapped(title:String)
    }
}
