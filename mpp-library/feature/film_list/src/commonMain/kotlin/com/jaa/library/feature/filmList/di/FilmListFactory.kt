
package com.jaa.library.feature.filmList.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import com.jaa.library.feature.filmList.presentation.FilmListViewModel
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.library.feature.filmList.useCase.ChangeFavouriteStateUseCaseInterface
import com.jaa.library.feature.filmList.useCase.GetFilmListUseCaseInterface

class FilmListFactory(
    private val filmTableDataFactoryInterface: FilmTableDataFactoryInterface,
    private val strings: FilmListViewModel.Strings
) {
    fun createFilmListViewModel(
        eventsDispatcher: EventsDispatcher<FilmListViewModel.EventsListener>,
        getFilmListUseCase: GetFilmListUseCaseInterface,
        changeFavouriteStateUseCaseInterface: ChangeFavouriteStateUseCaseInterface
    ) = FilmListViewModel(
        eventsDispatcher = eventsDispatcher,
        filmTableDataFactoryInterface = filmTableDataFactoryInterface,
        getFilmListUseCase = getFilmListUseCase,
        changeFavouriteStateUseCaseInterface = changeFavouriteStateUseCaseInterface,
        strings = strings
    )
}
