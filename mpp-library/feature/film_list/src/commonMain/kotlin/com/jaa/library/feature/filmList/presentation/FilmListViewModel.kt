
package com.jaa.library.feature.filmList.presentation

import com.jaa.library.feature.filmList.model.FilmRowData
import com.jaa.library.feature.filmList.useCase.*
import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.asState
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.units.TableUnitItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FilmListViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val filmTableDataFactory: FilmTableDataFactoryInterface,
    private val getNextPageInFilmListUseCase: GetNextPageInFilmListUseCaseInterface,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCaseInterface,
    private val filterByFavouriteUseCase: FilterByFavouriteUseCaseInterface,
    private val filterByTitleUseCase: FilterByTitleUseCaseInterface,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCaseInterface,
    private val setDownloadOnlyWithWifiUseCase: SetDownloadOnlyWithWifiUseCaseInterface,
    private val getFilmListUseCase: GetFilmListUseCaseInterface,
    private val strings: Strings,
    private val constants: Constants
) : ViewModel(), EventsDispatcherOwner<FilmListViewModel.EventsListener> {

    companion object {
        const val INDEX_OF_FAVOURITE_TAB = 1
    }

    private var currentTabSelected = 0

    private var automaticallyDownloadJob: Job? = null

    private val _state: MutableLiveData<State<List<FilmRowData>, Throwable>> =
        MutableLiveData(initialValue = State.Loading())

    val state: LiveData<State<List<TableUnitItem>, StringDesc>> = _state
        .dataTransform {
            map { films ->
                films.map {
                    filmTableDataFactory.createFilmRow(it.title.hashCode().toLong(), it, object : FilmTableDataFactoryInterface.ListRowTappedListener {
                        override fun onRowTapped(title: String) {
                            onListRowTapped(title)
                        }

                        override fun onFavouriteButtonTapped(title: String) {
                            onFilmFavouriteButtonTapped(title)
                        }
                    })
                }
            }
        }
        .errorTransform {
            // new type inferrence require set types oO
            map<Throwable, StringDesc> {
                it.message?.desc() ?: strings.unknownError.desc()
            }
        }

    fun onViewCreated() {
        prepareView()
    }

    fun onViewPresented() {
        updateFilmList()
        manageCurrentSettings()
    }

    fun onSettingsButtonPressed() {
        eventsDispatcher.dispatchEvent {
            presentSettingsView()
        }
    }

    fun onViewWillDisappear() {
        automaticallyDownloadJob?.cancel(null)
        automaticallyDownloadJob = null
    }

    fun getSearchString(): StringDesc {
        return StringDesc.Resource(strings.search)
    }

    private fun prepareView() {
        eventsDispatcher.dispatchEvent {
            setOnSearchBarTextChangedListener {
                onSearchTextChanged(text = it)
            }
            addTabToTabLayout(StringDesc.Resource(strings.allElements), 0)
            addTabToTabLayout(StringDesc.Resource(strings.favourites), 1)
            addOnTabLayoutChangedListener {
                onTabChanged(position = it)
            }
            addOnEndOfListReachedListener {
                onEndOfListReached()
            }
        }
    }

    private fun manageCurrentSettings() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase.execute(constants.downloadAutomaticallySettingKey, object: GetBooleanPreferenceUseCaseInterface.GetBooleanPreferenceModelListener {
                override fun onSuccess(value: Boolean) {
                    downloadAutomatically(value)
                    viewModelScope.launch {
                        getBooleanPreferenceUseCase.execute(constants.onlyWifiSettingKey, object: GetBooleanPreferenceUseCaseInterface.GetBooleanPreferenceModelListener {
                                override fun onSuccess(value: Boolean) {
                                    downloadOnlyWithWifi(value)
                                }
                            })
                    }
                }
            })
        }
    }

    private fun downloadAutomatically(active:Boolean) {
        if(active) {
            automaticallyDownloadJob = automaticallyDownloadJob ?: getAutomaticallyDownloadJob(active)
            automaticallyDownloadJob!!.start()
        } else {
            automaticallyDownloadJob?.cancel(null)
            automaticallyDownloadJob = null
        }
    }

    private fun getAutomaticallyDownloadJob(active:Boolean):Job {
        return viewModelScope.launch {
            while(active) {
                delay(60 * 1000)
                getNextPageInFilmList()
            }
        }
    }

    private fun downloadOnlyWithWifi(active:Boolean) {
        viewModelScope.launch {
            setDownloadOnlyWithWifiUseCase.execute(active, object:SetDownloadOnlyWithWifiUseCaseInterface.SetDownloadOnlyWithWifiModelListener {
                override fun onSuccess() {
                    // Download only with wifi option set
                }
            })
        }
    }

    private fun onSearchTextChanged(text: String) {
        viewModelScope.launch {
            filterByTitleUseCase.execute(text, object : FilterByTitleUseCaseInterface.FilterByTitleModelListener {
                override fun onSuccess(filmList: List<FilmRowData>) {
                    _state.value = filmList.asState()
                }
            })
        }
    }

    private fun onEndOfListReached() {
        if(currentTabSelected != INDEX_OF_FAVOURITE_TAB) {
            getNextPageInFilmList()
        }
    }

    private fun onListRowTapped(title:String) {
        val dataMap = mutableMapOf<String, String>()
        dataMap[constants.selectedFilmTitleKey] = title
        eventsDispatcher.dispatchEvent {
            presentFilmDetailView(dataMap)
        }
    }

    private fun onFilmFavouriteButtonTapped(title: String) {
        viewModelScope.launch {
            changeFavouriteStateUseCase.execute(title, object:ChangeFavouriteStateUseCaseInterface.ChangeFavouriteStateModelListener {
                override fun onSuccess(filmsUpdated: List<FilmRowData>) {
                    _state.value = filmsUpdated.asState()
                }

                override fun onError() {
                    eventsDispatcher.dispatchEvent {
                        showErrorMessage(getStringFromResource(strings.changeFavouriteStateError.desc()))
                    }
                }
            })
        }
    }

    private fun updateFilmList() {
        viewModelScope.launch {
            getFilmListUseCase.execute(object:GetFilmListUseCaseInterface.GetFilmListUseCaseModelListener {
                override fun onSuccess(films: List<FilmRowData>) {
                    if(films.isNotEmpty()) {
                        _state.value = films.asState()
                    } else {
                        getNextPageInFilmList()
                    }
                }
            })
        }
    }

    private fun getNextPageInFilmList() {
        eventsDispatcher.dispatchEvent {
            viewModelScope.launch {
                @Suppress("TooGenericExceptionCaught") // ktor on ios fail with Throwable when no network
                try {
                    getNextPageInFilmListUseCase.execute(isWifiActive(), object :
                        GetNextPageInFilmListUseCaseInterface.GetNextPageInFilmListModelListener {
                        override fun onSuccess(films: List<FilmRowData>) {
                            _state.value = films.asState()
                        }

                        override fun onError(e: Exception) {
                            showErrorMessage(e.message?:getStringFromResource(strings.unknownError.desc()))
                        }
                    })
                } catch (error: Throwable) {
                    _state.value = State.Error(error)
                }
            }
        }
    }

    private fun onTabChanged(position:Int) {
        currentTabSelected = position
        viewModelScope.launch {
            filterByFavouriteUseCase.execute(
                position == INDEX_OF_FAVOURITE_TAB,
                object : FilterByFavouriteUseCaseInterface.FilterByFavouriteModelListener {
                    override fun onSuccess(filmList: List<FilmRowData>) {
                        _state.value = filmList.asState()
                    }
                })
        }
    }

    interface EventsListener {
        fun addTabToTabLayout(tabText:StringDesc, position: Int)
        fun setOnSearchBarTextChangedListener(listener: (text:String) -> Unit)
        fun addOnTabLayoutChangedListener(listener: (position: Int) -> Unit)
        fun addOnEndOfListReachedListener(listener: () -> Unit)
        fun presentFilmDetailView(data:Map<String, String>)
        fun presentSettingsView()
        fun isWifiActive():Boolean
        fun getStringFromResource(resource: ResourceStringDesc) : String
        fun showErrorMessage(text: String)
    }

    interface Strings {
        val allElements: StringResource
        val favourites: StringResource
        val unknownError: StringResource
        val search: StringResource
        val changeFavouriteStateError: StringResource
    }

    interface Constants {
        val selectedFilmTitleKey:String
        val onlyWifiSettingKey: String
        val downloadAutomaticallySettingKey:String
    }
}
