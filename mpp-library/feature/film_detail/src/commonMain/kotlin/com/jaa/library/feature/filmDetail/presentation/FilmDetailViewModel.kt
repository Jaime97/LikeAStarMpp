
package com.jaa.library.feature.filmDetail.presentation

import com.jaa.library.feature.filmDetail.model.FilmDetail
import com.jaa.library.feature.filmDetail.useCase.GetFilmDetailUseCaseInterface
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class FilmDetailViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val getFilmDetailUseCase: GetFilmDetailUseCaseInterface
) : ViewModel(), EventsDispatcherOwner<FilmDetailViewModel.EventsListener> {


    fun onViewCreated() {
        eventsDispatcher.dispatchEvent {
            val position = getEntryData("position_key")?.toInt()
            if (position != null) {
                viewModelScope.launch {
                    getFilmDetailUseCase.execute(
                        position,
                        object : GetFilmDetailUseCaseInterface.GetFilmDetailModelListener {
                            override fun onSuccess(film: FilmDetail) {
                                updateFilmData(film)
                            }

                        })
                }
            }
        }
    }

    interface EventsListener {
        fun getEntryData(key:String): String?
        fun updateFilmData(film:FilmDetail)
    }

}
