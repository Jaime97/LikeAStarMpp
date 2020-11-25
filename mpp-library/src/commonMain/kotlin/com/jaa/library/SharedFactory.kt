/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.jaa.library

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.jaa.library.domain.di.DomainFactory
import com.jaa.library.domain.useCases.*
import com.jaa.library.feature.filmDetail.di.FilmDetailFactory
import com.jaa.library.feature.settings.di.SettingsFactory
import com.jaa.library.feature.filmDetail.model.FilmDetail
import com.jaa.library.feature.filmDetail.presentation.FilmDetailViewModel
import com.jaa.library.feature.settings.presentation.SettingsViewModel
import com.jaa.library.feature.filmDetail.useCase.ChangeVisitedStateUseCaseInterface
import com.jaa.library.feature.filmDetail.useCase.GetFilmDetailUseCaseInterface
import com.jaa.library.feature.filmList.di.FilmListFactory
import com.jaa.library.feature.filmList.model.FilmRowData
import com.jaa.library.feature.filmList.presentation.FilmListViewModel
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.library.feature.filmList.useCase.ChangeFavouriteStateUseCaseInterface
import com.jaa.library.feature.filmList.useCase.FilterByFavouriteUseCaseInterface
import com.jaa.library.feature.filmList.useCase.FilterByTitleUseCaseInterface
import com.jaa.library.feature.filmList.useCase.GetNextPageInFilmListUseCaseInterface
import com.jaa.library.feature.filmDetail.useCase.GetFilmImageUseCaseInterface
import com.jaa.library.feature.settings.presentation.SettingsTableDataFactoryInterface
import com.jaa.library.feature.settings.useCase.GetBooleanPreferenceUseCaseInterface
import com.jaa.library.feature.settings.useCase.ToggleBooleanPreferenceUseCaseInterface
import dev.icerock.moko.resources.StringResource
import com.squareup.sqldelight.db.SqlDriver
import dev.icerock.moko.network.generated.models.FilmData

class SharedFactory(
    antilog: Antilog,
    baseFilmUrl: String,
    baseFilmImageUrl: String,
    filmTableDataFactory: FilmTableDataFactoryInterface,
    settingsTableDataFactory: SettingsTableDataFactoryInterface,
    sqlDriver: SqlDriver
) {
    private val domainFactory = DomainFactory(
        baseFilmUrl = baseFilmUrl,
        baseFilmImageUrl = baseFilmImageUrl,
        sqlDriver = sqlDriver
    )

    val filmListFactory = FilmListFactory(
        filmTableDataFactoryInterface = filmTableDataFactory,
        strings = object : FilmListViewModel.Strings {
            override val allElements: StringResource = MR.strings.all_elements
            override val favourites: StringResource = MR.strings.favourites
            override val unknownError: StringResource = MR.strings.unknown_error
        },
        constants = object : FilmListViewModel.Constants {
            override val selectedFilmTitleKey: String = SELECTED_FILM_TITLE_KEY
        }
    )

    val filmDetailFactory = FilmDetailFactory(
        strings = object : FilmDetailViewModel.Strings {
            override val unknownError: StringResource = MR.strings.unknown_error
            override val selectLocation: StringResource = MR.strings.select_location
            override val sanFranciscoLocationSpec: StringResource = MR.strings.san_francisco_location_spec
        },
        constants = object : FilmDetailViewModel.Constants {
            override val selectedFilmTitleKey: String = SELECTED_FILM_TITLE_KEY
        }
    )

    val settingsFactory = SettingsFactory(
        settingsTableDataFactory = settingsTableDataFactory,
        strings = object : SettingsViewModel.Strings {
            override val onlyWifiSettingTitle: StringResource = MR.strings.only_wifi
            override val onlyWifiSettingDescription: StringResource = MR.strings.only_wifi_desc
            override val downloadAutomaticallySettingTitle: StringResource = MR.strings.download_automatically
            override val downloadAutomaticallySettingDescription: StringResource = MR.strings.download_automatically_desc
            override val unknownError: StringResource = MR.strings.unknown_error
        },
        constants = object : SettingsViewModel.Constants {
            override val onlyWifiSettingKey: String = ONLY_WIFI_SETTING_KEY
            override val downloadAutomaticallySettingKey: String = DOWNLOAD_AUTOMATICALLY_SETTING_KEY
        }
    )

    init {
        Napier.base(antilog)
    }

    fun getNextPageInFilmListUseCase():GetNextPageInFilmListUseCaseInterface {
        return mapGetNextPageInFilmListUseCase(GetNextPageInFilmListUseCase(domainFactory.filmListRepository))
    }

    private fun mapGetNextPageInFilmListUseCase(
        getNextPageInFilmListUseCase: GetNextPageInFilmListUseCase
    ) : GetNextPageInFilmListUseCaseInterface {
        return object : GetNextPageInFilmListUseCaseInterface {
            override suspend fun execute(listener: GetNextPageInFilmListUseCaseInterface.GetNextPageInFilmListModelListener) {
                getNextPageInFilmListUseCase.execute(object:GetNextPageInFilmListUseCase.GetNextPageInFilmListListener {
                    override fun onSuccess(films: List<FilmData>) {
                        listener.onSuccess(films.map { it.toFilmRowData() })
                    }

                })
            }
        }
    }

    fun changeFavouriteStateUseCase():ChangeFavouriteStateUseCaseInterface {
        return mapChangeFavouriteStateUseCase(ChangeFavouriteStateUseCase(domainFactory.filmListRepository))
    }

    private fun mapChangeFavouriteStateUseCase(
        changeFavouriteStateUseCase: ChangeFavouriteStateUseCase
    ) : ChangeFavouriteStateUseCaseInterface {
        return object : ChangeFavouriteStateUseCaseInterface {
            override suspend fun execute(
                title: String,
                listener: ChangeFavouriteStateUseCaseInterface.ChangeFavouriteStateModelListener
            ) {
                changeFavouriteStateUseCase.execute(title, object:ChangeFavouriteStateUseCase.ChangeFavouriteStateListener {
                    override fun onSuccess(filmsUpdated: List<FilmData>) {
                        listener.onSuccess(filmsUpdated.map { it.toFilmRowData() })
                    }
                })
            }
        }
    }

    fun filterByTitleUseCase():FilterByTitleUseCaseInterface {
        return  mapFilterByTitleUseCase(FilterByTitleUseCase(domainFactory.filmListRepository))
    }

    private fun mapFilterByTitleUseCase(
        filterByTitleUseCase: FilterByTitleUseCase
    ) : FilterByTitleUseCaseInterface {
        return object : FilterByTitleUseCaseInterface {
            override suspend fun execute(
                title: String,
                listener: FilterByTitleUseCaseInterface.FilterByTitleModelListener
            ) {
                filterByTitleUseCase.execute(title, object:FilterByTitleUseCase.FilterByTitleListener {
                    override fun onSuccess(filmList: List<FilmData>) {
                        listener.onSuccess(filmList.map { it.toFilmRowData() })
                    }
                })
            }
        }
    }

    fun filterByFavouriteUseCase():FilterByFavouriteUseCaseInterface {
        return mapFilterByFavouriteUseCase(FilterByFavouriteUseCase(domainFactory.filmListRepository))
    }

    private fun mapFilterByFavouriteUseCase(
        filterByFavouriteUseCase: FilterByFavouriteUseCase
    ) : FilterByFavouriteUseCaseInterface {
        return object : FilterByFavouriteUseCaseInterface {
            override suspend fun execute(
                filter: Boolean,
                listener: FilterByFavouriteUseCaseInterface.FilterByFavouriteModelListener
            ) {
                filterByFavouriteUseCase.execute(filter, object:FilterByFavouriteUseCase.FilterByFavouriteListener {
                    override fun onSuccess(filmList: List<FilmData>) {
                        listener.onSuccess(filmList = filmList.map { it.toFilmRowData() })
                    }
                })
            }

        }
    }

    fun getFilmDetailUseCase():GetFilmDetailUseCaseInterface {
        return mapGetFilmDetailUseCase(GetFilmDetailUseCase(domainFactory.filmDetailRepository))
    }

    private fun mapGetFilmDetailUseCase(
        getFilmDetailUseCase: GetFilmDetailUseCase
    ) : GetFilmDetailUseCaseInterface {
        return object : GetFilmDetailUseCaseInterface {
            override suspend fun execute(
                title: String,
                listener: GetFilmDetailUseCaseInterface.GetFilmDetailModelListener
            ) {
                getFilmDetailUseCase.execute(title, object:GetFilmDetailUseCase.GetFilmDetailListener {
                    override fun onSuccess(film: FilmData) {
                        listener.onSuccess(film = film.toFilmDetail())
                    }
                })
            }

        }
    }

    fun changeVisitedStateUseCase():ChangeVisitedStateUseCaseInterface {
        return mapChangeVisitedStateUseCase(ChangeVisitedStateUseCase(domainFactory.filmDetailRepository))
    }

    private fun mapChangeVisitedStateUseCase(
        changeVisitedStateUseCase: ChangeVisitedStateUseCase
    ) : ChangeVisitedStateUseCaseInterface {
        return object : ChangeVisitedStateUseCaseInterface {
            override suspend fun execute(
                filmTitle: String,
                listener: ChangeVisitedStateUseCaseInterface.ChangeVisitedStateModelListener
            ) {
                changeVisitedStateUseCase.execute(filmTitle, object:ChangeVisitedStateUseCase.ChangeVisitedStateListener {
                    override fun onSuccess(filmUpdated: FilmData) {
                        listener.onSuccess(filmUpdated = filmUpdated.toFilmDetail())
                    }
                })
            }
        }
    }

    fun getFilmImageUseCase():GetFilmImageUseCaseInterface {
        return mapGetFilmImageUseCase(GetFilmImageUseCase(domainFactory.filmDetailRepository))
    }

    private fun mapGetFilmImageUseCase(
        getFilmImageUseCase: GetFilmImageUseCase
    ) : GetFilmImageUseCaseInterface {
        return object : GetFilmImageUseCaseInterface {
            override suspend fun execute(
                title: String,
                listener: GetFilmImageUseCaseInterface.GetFilmImageModelListener
            ) {
                getFilmImageUseCase.execute(title, object:GetFilmImageUseCase.GetFilmImageListener {
                    override fun onSuccess(imageUrl: String) {
                        listener.onSuccess(imageUrl = imageUrl)
                    }

                    override fun onError() {
                        listener.onError()
                    }
                })
            }
        }
    }

    fun getBooleanPreferenceUseCase():GetBooleanPreferenceUseCaseInterface {
        return mapGetBooleanPreferenceUseCase(GetBooleanPreferenceUseCase(domainFactory.settingsRepository))
    }

    private fun mapGetBooleanPreferenceUseCase(
        getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase
    ) : GetBooleanPreferenceUseCaseInterface {
        return object : GetBooleanPreferenceUseCaseInterface {
            override suspend fun execute(
                key: String,
                listener: GetBooleanPreferenceUseCaseInterface.GetBooleanPreferenceModelListener
            ) {
                getBooleanPreferenceUseCase.execute(key, object:GetBooleanPreferenceUseCase.GetBooleanPreferenceListener {
                    override fun onSuccess(value: Boolean) {
                        listener.onSuccess(value)
                    }
                })
            }

        }
    }

    fun toggleBooleanPreferenceUseCase(): ToggleBooleanPreferenceUseCaseInterface {
        return mapToggleBooleanPreferenceUseCase(ToggleBooleanPreferenceUseCase(domainFactory.settingsRepository))
    }

    private fun mapToggleBooleanPreferenceUseCase(
        toggleBooleanPreferenceUseCase: ToggleBooleanPreferenceUseCase
    ) : ToggleBooleanPreferenceUseCaseInterface {
        return object : ToggleBooleanPreferenceUseCaseInterface {
            override suspend fun execute(
                key: String,
                listener: ToggleBooleanPreferenceUseCaseInterface.ToggleBooleanPreferenceModelListener
            ) {
                toggleBooleanPreferenceUseCase.execute(key, object:ToggleBooleanPreferenceUseCase.ToggleBooleanPreferenceListener {
                    override fun onSuccess() {
                        listener.onSuccess()
                    }
                })
            }

        }
    }

    fun FilmData.toFilmRowData() : FilmRowData {
        val titleValue = title
        val directorValue = director
        val favouriteValue = favourite
        val visitedValue = visited
        return object:FilmRowData {
            override val title: String = titleValue
            override val director: String = directorValue?:""
            override var favourite: Boolean = favouriteValue?:false
            override var visited: Boolean = visitedValue?:false
        }
    }

    fun FilmData.toFilmDetail() : FilmDetail {
        val titleValue = title
        val directorValue = director
        val locationsValue = locations
        val productionCompanyValue = productionCompany
        val actor1Value = actor1
        val visitedValue = visited
        return object:FilmDetail {
            override val title: String = titleValue
            override val director: String = directorValue?:""
            override val locations: String = locationsValue?:""
            override val productionCompany: String = productionCompanyValue?:""
            override val actor1: String = actor1Value?:""
            override var visited: Boolean = visitedValue?:false
        }
    }
}
