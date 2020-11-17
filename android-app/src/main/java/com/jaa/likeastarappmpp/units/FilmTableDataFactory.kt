package com.jaa.likeastarappmpp.units

import com.jaa.library.feature.filmList.model.Film
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.library.feature.filmList.presentation.ListRowTappedListener
import com.jaa.likeastarappmpp.FilmListRow
import dev.icerock.moko.units.TableUnitItem

class FilmTableDataFactory : FilmTableDataFactoryInterface {

    override fun createFilmRow(
        id: Long,
        film: Film,
        position: Int,
        listener: ListRowTappedListener
    ): TableUnitItem {
        return FilmListRow().apply {
            itemId = id
            this.film = film
            this.position = position
            this.listener = listener
        }
    }

}