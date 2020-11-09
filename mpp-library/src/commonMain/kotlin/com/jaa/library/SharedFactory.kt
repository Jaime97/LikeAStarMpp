/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.jaa.library

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.jaa.library.domain.di.DomainFactory
import com.jaa.library.domain.useCases.GetFilmListUseCase
import com.jaa.library.feature.filmList.di.FilmListFactory
import com.jaa.library.feature.filmList.model.Film
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface
import com.jaa.library.feature.filmList.useCase.GetFilmListUseCaseInterface
import com.squareup.sqldelight.db.SqlDriver
import dev.icerock.moko.network.generated.models.FilmData

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
        filmTableDataFactoryInterface = filmTableDataFactoryInterface
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
                        listener.onSuccess(films.map { Film(it.title, it.actor1?:"", it.director?:"", it.locations?:"", it.productionCompany?:"") })
                    }

                })
            }
        }
    }
}
