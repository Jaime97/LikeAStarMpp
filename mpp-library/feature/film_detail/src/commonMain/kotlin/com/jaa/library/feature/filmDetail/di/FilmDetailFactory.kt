
package com.jaa.library.feature.filmDetail.di

import com.jaa.library.feature.filmDetail.presentation.FilmDetailViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

class FilmDetailFactory(
) {
    fun createFilmDetailViewModel(
        eventsDispatcher: EventsDispatcher<FilmDetailViewModel.EventsListener>
    ) = FilmDetailViewModel(
        eventsDispatcher = eventsDispatcher
    )
}
