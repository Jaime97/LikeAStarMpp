
package com.jaa.library.feature.filmDetail.presentation

import com.jaa.library.feature.filmDetail.model.FilmDetail
import com.jaa.library.feature.filmDetail.useCase.ChangeVisitedStateUseCaseInterface
import com.jaa.library.feature.filmDetail.useCase.GetFilmDetailUseCaseInterface
import com.jaa.library.feature.filmDetail.useCase.GetFilmImageUseCaseInterface
import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.asState
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class FilmDetailViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val getFilmDetailUseCase: GetFilmDetailUseCaseInterface,
    private val changeVisitedStateUseCase: ChangeVisitedStateUseCaseInterface,
    private val getFilmImageUseCase: GetFilmImageUseCaseInterface,
    private val constants: Constants,
    private val strings: Strings
) : ViewModel(), EventsDispatcherOwner<FilmDetailViewModel.EventsListener> {

    private val _state: MutableLiveData<State<FilmDetail, Throwable>> =
        MutableLiveData(initialValue = State.Loading())

    val state: LiveData<State<FilmDetail, StringDesc>> = _state
        .errorTransform {
            map<Throwable, StringDesc> {
                it.message?.desc() ?: strings.unknownError.desc()
            }
        }

    fun onViewCreated() {
        eventsDispatcher.dispatchEvent {
            val currentFilmTitle = getEntryData(constants.selectedFilmTitleKey)
            if (currentFilmTitle != null) {
                viewModelScope.launch {
                    getFilmDetailUseCase.execute(
                        currentFilmTitle,
                        object : GetFilmDetailUseCaseInterface.GetFilmDetailModelListener {
                            override fun onSuccess(film: FilmDetail) {
                                _state.value = film.asState()
                                viewModelScope.launch {
                                    getFilmImageUseCase.execute(film.title, object:GetFilmImageUseCaseInterface.GetFilmImageModelListener {
                                        override fun onSuccess(imageUrl: String) {
                                            loadFilmImage(imageUrl)
                                        }

                                        override fun onError() {
                                            // Can't load image, leave placeholder
                                        }

                                    })
                                }
                            }
                        })
                }
            }
        }
    }

    fun onChangeVisitedStateButtonTapped() {
        viewModelScope.launch {
            if(_state.value.dataValue() != null) {
                changeVisitedStateUseCase.execute(
                    _state.value.dataValue()!!.title,
                    object : ChangeVisitedStateUseCaseInterface.ChangeVisitedStateModelListener {
                        override fun onSuccess(filmUpdated: FilmDetail) {
                            _state.value = filmUpdated.asState()
                        }
                    })
            }
        }
    }

    interface EventsListener {
        fun getEntryData(key:String): String?
        fun loadFilmImage(url:String)
    }

    interface Strings {
        val unknownError: StringResource
    }

    interface Constants {
        val selectedFilmTitleKey:String
    }

}
