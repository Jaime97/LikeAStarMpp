
package com.jaa.library.feature.filmDetail.di

import com.jaa.library.feature.filmDetail.presentation.FilmDetailViewModel
import com.jaa.library.feature.filmDetail.useCase.ChangeVisitedStateUseCaseInterface
import com.jaa.library.feature.filmDetail.useCase.GetFilmDetailUseCaseInterface
import com.jaa.library.feature.filmDetail.useCase.GetFilmImageUseCaseInterface
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

class FilmDetailFactory(
    private val strings: FilmDetailViewModel.Strings,
    private val constants: FilmDetailViewModel.Constants
) {
    fun createFilmDetailViewModel(
        eventsDispatcher: EventsDispatcher<FilmDetailViewModel.EventsListener>,
        getFilmDetailUseCase: GetFilmDetailUseCaseInterface,
        changeVisitedStateUseCase: ChangeVisitedStateUseCaseInterface,
        getFilmImageUseCase: GetFilmImageUseCaseInterface
    ) = FilmDetailViewModel(
        eventsDispatcher = eventsDispatcher,
        getFilmDetailUseCase = getFilmDetailUseCase,
        changeVisitedStateUseCase = changeVisitedStateUseCase,
        getFilmImageUseCase = getFilmImageUseCase,
        constants = constants,
        strings = strings
    )
}
