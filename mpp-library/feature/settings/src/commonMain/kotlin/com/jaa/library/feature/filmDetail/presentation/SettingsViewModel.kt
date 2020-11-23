
package com.jaa.library.feature.filmDetail.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class SettingsViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val constants: Constants,
    private val strings: Strings
) : ViewModel(), EventsDispatcherOwner<SettingsViewModel.EventsListener> {


    interface EventsListener {
    }

    interface Strings {
    }

    interface Constants {
    }

}
