
package com.jaa.library.feature.filmList.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import com.jaa.library.feature.filmList.presentation.FilmListViewModel
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.library.feature.filmList.useCase.*

class FilmListFactory(
    private val filmTableDataFactoryInterface: FilmTableDataFactoryInterface,
    private val strings: FilmListViewModel.Strings,
    private val constants: FilmListViewModel.Constants
) {
    fun createFilmListViewModel(
        eventsDispatcher: EventsDispatcher<FilmListViewModel.EventsListener>,
        getNextPageInFilmListUseCase: GetNextPageInFilmListUseCaseInterface,
        changeFavouriteStateUseCase: ChangeFavouriteStateUseCaseInterface,
        filterByTitleUseCase: FilterByTitleUseCaseInterface,
        filterByFavouriteUseCase: FilterByFavouriteUseCaseInterface,
        getBooleanPreferenceUseCase: GetBooleanPreferenceUseCaseInterface,
        setDownloadOnlyWithWifiUseCase: SetDownloadOnlyWithWifiUseCaseInterface,
        getFilmListUseCase: GetFilmListUseCaseInterface
    ) = FilmListViewModel(
        eventsDispatcher = eventsDispatcher,
        filmTableDataFactory = filmTableDataFactoryInterface,
        getNextPageInFilmListUseCase = getNextPageInFilmListUseCase,
        changeFavouriteStateUseCase = changeFavouriteStateUseCase,
        filterByFavouriteUseCase = filterByFavouriteUseCase,
        filterByTitleUseCase = filterByTitleUseCase,
        getBooleanPreferenceUseCase = getBooleanPreferenceUseCase,
        setDownloadOnlyWithWifiUseCase = setDownloadOnlyWithWifiUseCase,
        getFilmListUseCase = getFilmListUseCase,
        strings = strings,
        constants = constants
    )
}
