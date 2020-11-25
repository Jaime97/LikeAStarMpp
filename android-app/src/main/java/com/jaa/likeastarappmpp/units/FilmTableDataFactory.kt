package com.jaa.likeastarappmpp.units

import com.jaa.library.feature.filmList.model.FilmRowData
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.likeastarappmpp.FilmListRow
import dev.icerock.moko.units.TableUnitItem

class FilmTableDataFactory : FilmTableDataFactoryInterface {

    override fun createFilmRow(
        id: Long,
        film: FilmRowData,
        listener: FilmTableDataFactoryInterface.ListRowTappedListener
    ): TableUnitItem {
        return FilmListRow().apply {
            itemId = id
            this.film = film
            this.listener = listener
        }
    }

}