/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.jaa.library

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.jaa.library.feature.filmList.di.FilmListFactory
import com.jaa.library.feature.filmList.presentation.FilmTableDataFactoryInterface

class SharedFactory(
    antilog: Antilog,
    filmTableDataFactoryInterface: FilmTableDataFactoryInterface
) {
    val filmListFactory = FilmListFactory(
        filmTableDataFactoryInterface = filmTableDataFactoryInterface
    )

    init {
        Napier.base(antilog)
    }
}
