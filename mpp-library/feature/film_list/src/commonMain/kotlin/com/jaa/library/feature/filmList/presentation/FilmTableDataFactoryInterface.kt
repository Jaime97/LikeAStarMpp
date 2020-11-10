package com.jaa.library.feature.filmList.presentation

import dev.icerock.moko.units.TableUnitItem

interface FilmTableDataFactoryInterface {
    fun createFilmRow(id: Long,title: String, director: String, isFavourite: Boolean, isVisited: Boolean): TableUnitItem
}