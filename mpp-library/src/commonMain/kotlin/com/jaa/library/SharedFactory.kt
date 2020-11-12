/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.jaa.library

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.jaa.library.domain.di.DomainFactory
import com.jaa.library.domain.useCases.ChangeFavouriteStateUseCase
import com.jaa.library.domain.useCases.GetFilmListUseCase
import com.jaa.library.feature.filmList.di.FilmListFactory
import com.jaa.library.feature.filmList.model.Film
import com.jaa.library.feature.filmList.presentation.FilmListViewModel
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.library.feature.filmList.useCase.ChangeFavouriteStateUseCaseInterface
import com.jaa.library.feature.filmList.useCase.GetFilmListUseCaseInterface
import dev.icerock.moko.network.generated.models.FilmData
import dev.icerock.moko.resources.StringResource
import com.squareup.sqldelight.db.SqlDriver

class SharedFactory(
    antilog: Antilog,
    baseUrl: String,
    filmTableDataFactoryInterface: FilmTableDataFactoryInterface,
    sqlDriver: SqlDriver
) {
    private val domainFactory = DomainFactory(
        baseUrl = baseUrl,
        sqlDriver = sqlDriver
    )

    val filmListFactory = FilmListFactory(
        filmTableDataFactoryInterface = filmTableDataFactoryInterface,
        strings = object : FilmListViewModel.Strings {
            override val allElements: StringResource = MR.strings.all_elements
            override val favourites: StringResource = MR.strings.favourites
            override val unknownError: StringResource = MR.strings.unknown_error
        }
    )

    init {
        Napier.base(antilog)
    }

    fun getFilmListUseCase():GetFilmListUseCaseInterface {
        return mapFilmListUseCase(GetFilmListUseCase(domainFactory.filmListRepository))
    }

    private fun mapFilmListUseCase(
        getFilmListUseCase: GetFilmListUseCase
    ) : GetFilmListUseCaseInterface {
        return object : GetFilmListUseCaseInterface {
            override suspend fun execute(listener: GetFilmListUseCaseInterface.GetFilmListModelListener) {
                getFilmListUseCase.execute(object:GetFilmListUseCase.GetFilmListListener {
                    override fun onSuccess(films: List<FilmData>) {
                        listener.onSuccess(films.map { Film(it.title, it.actor1?:"", it.director?:"", it.locations?:"",
                            it.productionCompany?:"", it.favourite?:false, it.visited?:false) })
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
                position: Int,
                listener: ChangeFavouriteStateUseCaseInterface.ChangeFavouriteStateModelListener
            ) {
                changeFavouriteStateUseCase.execute(position, object:ChangeFavouriteStateUseCase.ChangeFavouriteStateListener {
                    override fun onSuccess(filmsUpdated: List<FilmData>) {
                        listener.onSuccess(filmsUpdated.map { Film(it.title, it.actor1?:"", it.director?:"", it.locations?:"",
                            it.productionCompany?:"", it.favourite?:false, it.visited?:false) })
                    }

                })
            }
        }
    }
}
