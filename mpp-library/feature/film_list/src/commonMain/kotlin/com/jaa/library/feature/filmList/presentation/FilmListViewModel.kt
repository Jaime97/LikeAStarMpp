
package com.jaa.library.feature.filmList.presentation

import com.jaa.library.feature.filmList.model.FilmRowData
import com.jaa.library.feature.filmList.useCase.ChangeFavouriteStateUseCaseInterface
import com.jaa.library.feature.filmList.useCase.FilterByFavouriteUseCaseInterface
import com.jaa.library.feature.filmList.useCase.FilterByTitleUseCaseInterface
import com.jaa.library.feature.filmList.useCase.GetNextPageInFilmListUseCaseInterface
import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.asState
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.units.TableUnitItem
import kotlinx.coroutines.launch

class FilmListViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val filmTableDataFactory: FilmTableDataFactoryInterface,
    private val getNextPageInFilmListUseCase: GetNextPageInFilmListUseCaseInterface,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCaseInterface,
    private val filterByFavouriteUseCase: FilterByFavouriteUseCaseInterface,
    private val filterByTitleUseCase: FilterByTitleUseCaseInterface,
    private val strings: Strings,
    private val constants: Constants
) : ViewModel(), EventsDispatcherOwner<FilmListViewModel.EventsListener> {

    companion object {
        const val INDEX_OF_FAVOURITE_TAB = 1
    }

    private var currentTabSelected = 0

    private val _state: MutableLiveData<State<List<FilmRowData>, Throwable>> =
        MutableLiveData(initialValue = State.Loading())

    val state: LiveData<State<List<TableUnitItem>, StringDesc>> = _state
        .dataTransform {
            map { films ->
                films.map {
                    filmTableDataFactory.createFilmRow(it.title.hashCode().toLong(), it, object : ListRowTappedListener {
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

    fun onViewPresented() {
        getNextPageInFilmList()
    }

    fun onSettingsButtonPressed() {

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
            })
        }
    }

    private fun getNextPageInFilmList() {
        viewModelScope.launch {
            try {
                getNextPageInFilmListUseCase.execute(object : GetNextPageInFilmListUseCaseInterface.GetNextPageInFilmListModelListener {
                    override fun onSuccess(films: List<FilmRowData>) {
                        _state.value = films.asState()
                    }
                })
            } catch (error: Throwable) {
                _state.value = State.Error(error)
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
    }

    interface Strings {
        val allElements: StringResource
        val favourites: StringResource
        val unknownError: StringResource
    }

    interface Constants {
        val selectedFilmTitleKey:String
    }
}
