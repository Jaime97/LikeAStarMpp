
package com.jaa.library.feature.filmList.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import com.jaa.library.feature.filmList.presentation.FilmListViewModel
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface

class FilmListFactory(
    private val filmTableDataFactoryInterface: FilmTableDataFactoryInterface,
    private val strings: FilmListViewModel.Strings
) {
    fun createFilmListViewModel(
        eventsDispatcher: EventsDispatcher<FilmListViewModel.EventsListener>
    ) = FilmListViewModel(
        eventsDispatcher = eventsDispatcher,
        filmTableDataFactoryInterface = filmTableDataFactoryInterface,
        getFilmListUseCase = getFilmListUseCase,
        strings = strings
    )
}
