/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.jaa.likeastarappmpp

import android.app.Application
import com.github.aakira.napier.DebugAntilog
import com.jaa.library.SharedFactory
import com.jaa.likeastarappmpp.units.FilmTableDataFactory

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppComponent.factory = SharedFactory(
            antilog = DebugAntilog(),
            filmTableDataFactoryInterface = FilmTableDataFactory()
        )
    }
}
