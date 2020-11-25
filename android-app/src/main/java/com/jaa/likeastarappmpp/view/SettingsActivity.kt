package com.jaa.likeastarappmpp.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jaa.library.feature.settings.presentation.SettingsViewModel
import com.jaa.likeastarappmpp.AppComponent
import com.jaa.likeastarappmpp.R
import com.jaa.likeastarappmpp.databinding.ActivitySettingsBinding
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.resources.desc.ResourceStringDesc


class SettingsActivity :
    MvvmEventsActivity<ActivitySettingsBinding, SettingsViewModel, SettingsViewModel.EventsListener>(),
    SettingsViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_settings
    override val viewModelClass: Class<SettingsViewModel> = SettingsViewModel::class.java
    override val viewModelVariableId: Int = com.jaa.likeastarappmpp.BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.settingsFactory.createSettingsViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            getBooleanPreferenceUseCase = AppComponent.factory.getBooleanPreferenceUseCase(),
            toggleBooleanPreferenceUseCase = AppComponent.factory.toggleBooleanPreferenceUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onViewCreated()
    }

    override fun getStringFromResource(resource: ResourceStringDesc): String {
        return resource.toString(this)
    }

}