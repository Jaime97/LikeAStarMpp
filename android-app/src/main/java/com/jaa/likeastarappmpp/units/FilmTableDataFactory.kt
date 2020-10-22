package com.jaa.likeastarappmpp.units

import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.likeastarappmpp.FilmListRow
import dev.icerock.moko.units.TableUnitItem

class FilmTableDataFactory : FilmTableDataFactoryInterface {

    override fun createFilmRow(
        title: String,
        director: String,
        isFavourite: Boolean,
        isVisited: Boolean
    ): TableUnitItem {
        return FilmListRow().apply {
            this.title = title
            this.director = director
            this.isFavourite = isFavourite
            this.isVisited = isVisited
        }
    }

}