
package com.jaa.library.feature.filmList.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class FilmListViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    val filmTableDataFactoryInterface: FilmTableDataFactoryInterface
) : ViewModel(), EventsDispatcherOwner<FilmListViewModel.EventsListener> {

    val items = listOf(
        filmTableDataFactoryInterface.createFilmRow("title", "director", true, true)
    )

    fun onViewCreated() {
        eventsDispatcher.dispatchEvent {
            addTabToTabLayout(getString("all_elements"), 0)
            addTabToTabLayout(getString("favourites"), 1)
            configureOnTabSelectedListener {
                onTabSelected(position = it)
            }
        }
    }

    fun onTabSelected(position: Int) {

    }

    fun onSettingsButtonPressed() {

    }

    fun onTabChanged(position:Int) {

    }

    interface EventsListener {
        fun addTabToTabLayout(tabText:String, position: Int)
        fun configureOnTabSelectedListener(listener: (position: Int) -> Unit)
        fun getString(key:String):String
    }
}
