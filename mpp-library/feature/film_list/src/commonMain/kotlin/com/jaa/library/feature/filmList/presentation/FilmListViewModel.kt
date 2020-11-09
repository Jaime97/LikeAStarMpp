
package com.jaa.library.feature.filmList.presentation

import com.jaa.library.feature.filmList.model.Film
import com.jaa.library.feature.filmList.useCase.GetFilmListUseCaseInterface
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class FilmListViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val filmTableDataFactoryInterface: FilmTableDataFactoryInterface,
    private val getFilmListUseCase: GetFilmListUseCaseInterface,
    private val strings: Strings,
) : ViewModel(), EventsDispatcherOwner<FilmListViewModel.EventsListener> {

    var items = listOf(
        filmTableDataFactoryInterface.createFilmRow("title", "director", true, true)
    )

    private fun onTabSelected(position: Int) {

    }

    private fun onSearchTextChanged(text: String) {

    }

    private fun getFilmList() {
        viewModelScope.launch {
            getFilmListUseCase.execute(object : GetFilmListUseCaseInterface.GetFilmListModelListener {
                override fun onSuccess(films: List<Film>) {
                    items = films.map { filmTableDataFactoryInterface.createFilmRow(it.title, it.director, it.favourite, it.visited) }
                }

            })
        }
    }

    fun onViewCreated() {
        eventsDispatcher.dispatchEvent {
            setOnSearchBarTextChangedListener {
                onSearchTextChanged(text = it)
            }
            addTabToTabLayout(StringDesc.Resource(strings.allElements), 0)
            addTabToTabLayout(StringDesc.Resource(strings.favourites), 1)
            configureOnTabSelectedListener {
                onTabSelected(position = it)
            }
        }
        getFilmList()
    }

    fun onSettingsButtonPressed() {

    }

    fun onTabChanged(position:Int) {

    }

    interface EventsListener {
        fun addTabToTabLayout(tabText:StringDesc, position: Int)
        fun configureOnTabSelectedListener(listener: (position: Int) -> Unit)
        fun setOnSearchBarTextChangedListener(listener: (text:String) -> Unit)
    }

    interface Strings {
        val allElements: StringResource
        val favourites: StringResource
    }
}
