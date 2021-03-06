/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.kotlinAndroidExtensions)
    plugin(Deps.Plugins.kotlinSerialization)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mokoNetwork)
    plugin(Deps.Plugins.sqlDelight)
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)
    commonMainImplementation(Deps.Libs.MultiPlatform.kotlinSerialization)
    commonMainImplementation(Deps.Libs.MultiPlatform.ktorClient)
    commonMainImplementation(Deps.Libs.MultiPlatform.ktorClientLogging)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoParcelize.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoNetwork.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.napier.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.multiplatformSettings.common)
    mppLibrary(Deps.Libs.MultiPlatform.sqlDelight)

    commonTestImplementation(Deps.Libs.MultiPlatform.mokoMvvm.common)
    commonTestImplementation(Deps.Libs.MultiPlatform.Tests.kotlinTest)
    commonTestImplementation(Deps.Libs.MultiPlatform.Tests.kotlinTestAnnotations)
}

sqldelight {
    database("FilmSqlDatabase") {
        packageName = "com.jaa.library.domain.dataSource.storage"
    }
}

openApiGenerate {
    inputSpec.set(file("src/openapi.yml").path)
    generatorName.set("kotlin-ktor-client")
    additionalProperties.set(mutableMapOf("nonPublicApi" to "false"))
}

