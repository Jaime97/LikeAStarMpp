
package com.jaa.library.feature.filmList.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import com.jaa.library.feature.filmList.presentation.FilmListViewModel
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.library.feature.filmList.useCase.ChangeFavouriteStateUseCaseInterface
import com.jaa.library.feature.filmList.useCase.FilterByFavouriteUseCaseInterface
import com.jaa.library.feature.filmList.useCase.GetNextPageInFilmListUseCaseInterface

class FilmListFactory(
    private val filmTableDataFactoryInterface: FilmTableDataFactoryInterface,
    private val strings: FilmListViewModel.Strings
) {
    fun createFilmListViewModel(
        eventsDispatcher: EventsDispatcher<FilmListViewModel.EventsListener>,
        getNextPageInFilmListUseCase: GetNextPageInFilmListUseCaseInterface,
        changeFavouriteStateUseCase: ChangeFavouriteStateUseCaseInterface,
        filterByFavouriteUseCase: FilterByFavouriteUseCaseInterface
    ) = FilmListViewModel(
        eventsDispatcher = eventsDispatcher,
        filmTableDataFactoryInterface = filmTableDataFactoryInterface,
        getNextPageInFilmListUseCase = getNextPageInFilmListUseCase,
        changeFavouriteStateUseCase = changeFavouriteStateUseCase,
        filterByFavouriteUseCase = filterByFavouriteUseCase,
        strings = strings
    )
}
