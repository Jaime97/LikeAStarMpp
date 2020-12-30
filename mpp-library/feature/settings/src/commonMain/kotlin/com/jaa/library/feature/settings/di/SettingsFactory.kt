
package com.jaa.library.feature.settings.di

import com.jaa.library.feature.settings.presentation.SettingsTableDataFactoryInterface
import com.jaa.library.feature.settings.presentation.SettingsViewModel
import com.jaa.library.feature.settings.useCase.GetBooleanPreferenceUseCaseInterface
import com.jaa.library.feature.settings.useCase.ToggleBooleanPreferenceUseCaseInterface
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

class SettingsFactory(
    private val settingsTableDataFactory: SettingsTableDataFactoryInterface,
    private val strings: SettingsViewModel.Strings,
    private val constants: SettingsViewModel.Constants
) {
    fun createSettingsViewModel(
        eventsDispatcher: EventsDispatcher<SettingsViewModel.EventsListener>,
        getBooleanPreferenceUseCase: GetBooleanPreferenceUseCaseInterface,
        toggleBooleanPreferenceUseCase: ToggleBooleanPreferenceUseCaseInterface
    ) = SettingsViewModel(
        eventsDispatcher = eventsDispatcher,
        settingsTableDataFactory = settingsTableDataFactory,
        getBooleanPreferenceUseCase = getBooleanPreferenceUseCase,
        toggleBooleanPreferenceUseCase = toggleBooleanPreferenceUseCase,
        constants = constants,
        strings = strings
    )
}
