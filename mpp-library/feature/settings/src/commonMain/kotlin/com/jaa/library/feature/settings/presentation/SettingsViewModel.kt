
package com.jaa.library.feature.settings.presentation

import com.jaa.library.feature.settings.model.SettingsRowData
import com.jaa.library.feature.settings.useCase.GetBooleanPreferenceUseCaseInterface
import com.jaa.library.feature.settings.useCase.ToggleBooleanPreferenceUseCaseInterface
import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.asState
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.units.TableUnitItem
import kotlinx.coroutines.launch

class SettingsViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val settingsTableDataFactory: SettingsTableDataFactoryInterface,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCaseInterface,
    private val toggleBooleanPreferenceUseCase: ToggleBooleanPreferenceUseCaseInterface,
    private val constants: Constants,
    private val strings: Strings
) : ViewModel(), EventsDispatcherOwner<SettingsViewModel.EventsListener> {

    private val _state: MutableLiveData<State<List<SettingsRowData>, Throwable>> =
        MutableLiveData(initialValue = State.Loading())

    val state: LiveData<State<List<TableUnitItem>, StringDesc>> = _state
        .dataTransform {
            map { settings ->
                settings.map {
                    settingsTableDataFactory.createSettingsRow(it.key.hashCode().toLong(), it, object :SettingsTableDataFactoryInterface.OnchangeActiveStateListener {
                        override fun onChange(settingKey: String) {
                            toggleSetting(settingKey)
                        }
                    })
                }
            }
        }
        .errorTransform {
            map<Throwable, StringDesc> {
                it.message?.desc() ?: strings.unknownError.desc()
            }
        }

    fun onViewCreated() {
        eventsDispatcher.dispatchEvent {
            getSetting(constants.onlyWifiSettingKey, getStringFromResource(strings.onlyWifiSettingTitle.desc()),
                getStringFromResource(strings.onlyWifiSettingDescription.desc()))
            getSetting(constants.downloadAutomaticallySettingKey, getStringFromResource(strings.downloadAutomaticallySettingTitle.desc()),
                getStringFromResource(strings.downloadAutomaticallySettingDescription.desc()))
        }
    }

    private fun getSetting(key:String, title:String, description:String) {
        viewModelScope.launch {
            getBooleanPreferenceUseCase.execute(key, object:GetBooleanPreferenceUseCaseInterface.GetBooleanPreferenceModelListener {
                override fun onSuccess(value: Boolean) {
                    val setting = SettingsRowData(title, key, description, value)
                    appendSettingToList(setting)
                }
            })
        }
    }

    private fun appendSettingToList(setting:SettingsRowData) {
        val newList:MutableList<SettingsRowData> = if(_state.value.dataValue() == null) {
            mutableListOf()
        } else {
            _state.value.dataValue()!!.toMutableList()
        }
        newList.add(setting)
        _state.value = newList.asState()
    }

    private fun toggleSetting(key:String) {
        viewModelScope.launch {
            toggleBooleanPreferenceUseCase.execute(key, object: ToggleBooleanPreferenceUseCaseInterface.ToggleBooleanPreferenceModelListener {
                override fun onSuccess() {
                    // Value correctly saved
                }
            })
        }
    }

    interface EventsListener {
        fun getStringFromResource(resource: ResourceStringDesc) : String
    }

    interface Strings {
        val onlyWifiSettingTitle: StringResource
        val onlyWifiSettingDescription: StringResource
        val downloadAutomaticallySettingTitle: StringResource
        val downloadAutomaticallySettingDescription: StringResource
        val unknownError: StringResource
    }

    interface Constants {
        val onlyWifiSettingKey: String
        val downloadAutomaticallySettingKey:String
    }

}
