package com.jaa.library.feature.filmList.presentation

import com.jaa.library.feature.filmList.model.Film
import dev.icerock.moko.units.TableUnitItem

interface FilmTableDataFactoryInterface {
    fun createFilmRow(id: Long, film: Film, position: Int, listener: ListRowTappedListener): TableUnitItem
}