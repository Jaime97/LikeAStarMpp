
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
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class FilmDetailViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val getFilmDetailUseCase: GetFilmDetailUseCaseInterface,
    private val changeVisitedStateUseCase: ChangeVisitedStateUseCaseInterface,
    private val getFilmImageUseCase: GetFilmImageUseCaseInterface,
    private val constants: Constants,
    private val strings: Strings,
    val permissionsController: PermissionsController
) : ViewModel(), EventsDispatcherOwner<FilmDetailViewModel.EventsListener> {

    private val _state: MutableLiveData<State<FilmDetail, Throwable>> =
        MutableLiveData(initialValue = State.Loading())

    val state: LiveData<State<FilmDetail, StringDesc>> = _state
        .errorTransform {
            map {
                it.message?.desc() ?: strings.unknownError.desc()
            }
        }

    private var splittedLocations:Array<String> = emptyArray()

    fun onViewCreated() {
        state.addObserver {
            splittedLocations = if(it.isSuccess())state.value.dataValue()!!.locations.split(";").filter { location -> location != "null" }.toTypedArray() else emptyArray()
        }
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

                                        override fun onError(e: Exception?) {
                                            // Can't load image, leave placeholder
                                            showErrorMessage(e?.message?:getStringFromResource(strings.filmImageError.desc()))
                                        }
                                    })
                                }
                            }
                        })
                }
            }
        }
    }

    fun onLocationsButtonTapped() {

        if(splittedLocations.isNotEmpty()) {
            requestPermission(Permission.LOCATION)
        } else {
            eventsDispatcher.dispatchEvent {
                showAlert(strings.noLocationError.desc(), strings.noLocationErrorDesc.desc(), strings.ok.desc())
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

    // Get common resources from iOS

    fun getSeeLocationsString(): StringDesc {
        return StringDesc.Resource(strings.seeLocations)
    }

    fun getVisitedButtonString(): StringDesc {
        return StringDesc.Resource(strings.visitedButton)
    }

    fun getStarringByString(): StringDesc {
        return StringDesc.Resource(strings.starringBy)
    }

    fun getDirectedByString(): StringDesc {
        return StringDesc.Resource(strings.directedBy)
    }

    fun getProducedByString(): StringDesc {
        return StringDesc.Resource(strings.producedBy)
    }

    fun getUnvisitedButtonString(): StringDesc {
        return StringDesc.Resource(strings.unvisitedButton)
    }

    private fun requestPermission(permission: Permission) {
        viewModelScope.launch {
            try {
                // Calls suspend function in a com.jaa.library.domain.coroutine to request some permission.
                permissionsController.providePermission(permission)
                // If there are no exceptions, permission has been granted successfully.
                showLocations()
            } catch (deniedAlwaysException: DeniedAlwaysException) {
                eventsDispatcher.dispatchEvent {
                    showAlert(strings.permissionErrorTitle.desc(), strings.permissionErrorDesc.desc(), strings.ok.desc())
                }
            } catch (deniedException: DeniedException) {
                eventsDispatcher.dispatchEvent {
                    showAlert(strings.permissionErrorTitle.desc(), strings.permissionErrorDesc.desc(), strings.ok.desc())
                }
            }
        }
    }

    private fun showLocations() {
        eventsDispatcher.dispatchEvent {
            showListInDialog(strings.selectLocation.desc(), splittedLocations) { position ->
                geUserLocation(onSuccessListener = { lat, long ->
                    openMapWithLocation(
                        Pair(lat, long),
                        splittedLocations[position],
                        strings.sanFranciscoLocationSpec.desc()
                    )
                }, onErrorListener = {
                    showAlert(strings.userLocationError.desc(), strings.userLocationErrorDesc.desc(), strings.ok.desc())
                })
            }
        }
    }

    interface EventsListener {
        fun getEntryData(key:String): String?
        fun loadFilmImage(url:String)
        fun showListInDialog(title:StringDesc, elementList:Array<String>, onRowTappedListener:(position:Int) -> Unit)
        fun openMapWithLocation(originCoordinates: Pair<Double, Double>, destinyLocation:String, suffix:StringDesc)
        fun showAlert(title:StringDesc, description:StringDesc, buttonTitle:StringDesc)
        fun geUserLocation(onSuccessListener:(latitude: Double, longitude: Double) -> Unit, onErrorListener: () -> Unit)
        fun getStringFromResource(resource: ResourceStringDesc) : String
        fun showErrorMessage(text: String)
    }

    interface Strings {
        val unknownError: StringResource
        val selectLocation: StringResource
        val sanFranciscoLocationSpec: StringResource
        val seeLocations: StringResource
        val visitedButton: StringResource
        val unvisitedButton: StringResource
        val starringBy: StringResource
        val producedBy: StringResource
        val directedBy: StringResource
        val permissionErrorTitle: StringResource
        val permissionErrorDesc: StringResource
        val ok: StringResource
        val noLocationError: StringResource
        val noLocationErrorDesc: StringResource
        val userLocationError: StringResource
        val userLocationErrorDesc: StringResource
        val filmImageError: StringResource
    }

    interface Constants {
        val selectedFilmTitleKey:String
    }

}
