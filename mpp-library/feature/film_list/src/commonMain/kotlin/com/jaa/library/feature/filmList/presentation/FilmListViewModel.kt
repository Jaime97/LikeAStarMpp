
package com.jaa.library.feature.filmList.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class FilmListViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<FilmListViewModel.EventsListener> {



    fun onSettingsButtonPressed() {

    }

    fun onTabChanged(position:Int) {

    }

    interface EventsListener {
    }
}
