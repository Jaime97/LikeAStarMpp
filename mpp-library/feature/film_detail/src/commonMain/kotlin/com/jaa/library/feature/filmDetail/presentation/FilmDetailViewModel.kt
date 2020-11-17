
package com.jaa.library.feature.filmDetail.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class FilmDetailViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<FilmDetailViewModel.EventsListener> {


    fun onViewCreated() {

    }

    interface EventsListener {

    }

}
