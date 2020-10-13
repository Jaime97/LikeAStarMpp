
package com.jaa.library.feature.filmList.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import com.jaa.library.feature.filmList.presentation.FilmListViewModel

class FilmListFactory() {
    fun createFilmListViewModel(
        eventsDispatcher: EventsDispatcher<FilmListViewModel.EventsListener>
    ) = FilmListViewModel(
        eventsDispatcher = eventsDispatcher
    )
}
