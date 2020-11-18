
package com.jaa.library.feature.filmDetail.di

import com.jaa.library.feature.filmDetail.presentation.FilmDetailViewModel
import com.jaa.library.feature.filmDetail.useCase.GetFilmDetailUseCaseInterface
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

class FilmDetailFactory(

) {
    fun createFilmDetailViewModel(
        eventsDispatcher: EventsDispatcher<FilmDetailViewModel.EventsListener>,
        getFilmDetailUseCase: GetFilmDetailUseCaseInterface
    ) = FilmDetailViewModel(
        eventsDispatcher = eventsDispatcher,
        getFilmDetailUseCase = getFilmDetailUseCase
    )
}
