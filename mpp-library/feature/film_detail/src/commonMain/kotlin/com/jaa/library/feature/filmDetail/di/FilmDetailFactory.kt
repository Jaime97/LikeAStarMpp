
package com.jaa.library.feature.filmDetail.di

import com.jaa.library.feature.filmDetail.presentation.FilmDetailViewModel
import com.jaa.library.feature.filmDetail.useCase.ChangeVisitedStateUseCaseInterface
import com.jaa.library.feature.filmDetail.useCase.GetFilmDetailUseCaseInterface
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

class FilmDetailFactory(
    private val strings: FilmDetailViewModel.Strings,
    private val constants: FilmDetailViewModel.Constants
) {
    fun createFilmDetailViewModel(
        eventsDispatcher: EventsDispatcher<FilmDetailViewModel.EventsListener>,
        getFilmDetailUseCase: GetFilmDetailUseCaseInterface,
        changeVisitedStateUseCase: ChangeVisitedStateUseCaseInterface
    ) = FilmDetailViewModel(
        eventsDispatcher = eventsDispatcher,
        getFilmDetailUseCase = getFilmDetailUseCase,
        changeVisitedStateUseCase = changeVisitedStateUseCase,
        constants = constants,
        strings = strings
    )
}
