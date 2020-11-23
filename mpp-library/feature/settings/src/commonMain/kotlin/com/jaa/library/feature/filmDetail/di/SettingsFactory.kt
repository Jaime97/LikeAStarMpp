
package com.jaa.library.feature.filmDetail.di

import com.jaa.library.feature.filmDetail.presentation.SettingsViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

class SettingsFactory(
    private val strings: SettingsViewModel.Strings,
    private val constants: SettingsViewModel.Constants
) {
    fun createSettingsViewModel(
        eventsDispatcher: EventsDispatcher<SettingsViewModel.EventsListener>,
    ) = SettingsViewModel(
        eventsDispatcher = eventsDispatcher,
        constants = constants,
        strings = strings
    )
}
