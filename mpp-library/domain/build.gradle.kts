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

object Versions {
    const val sqlDelight = "1.4.4"
}
val sqlDelight = MultiPlatformLibrary(
    common = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}",
    android = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}",
    ios = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
)

dependencies {
    mppLibrary(sqlDelight)
    ///actual NativeSqliteDriver somehow not found on iosMain package because this template is using their own plugin @see https://github.com/icerockdev/mobile-multiplatform-gradle-plugin , workaround
    iosMainApi("com.squareup.sqldelight:native-driver:${Versions.sqlDelight}")
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)
    commonMainImplementation(Deps.Libs.MultiPlatform.kotlinSerialization)
    commonMainImplementation(Deps.Libs.MultiPlatform.ktorClient)
    commonMainImplementation(Deps.Libs.MultiPlatform.ktorClientLogging)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoParcelize.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoNetwork.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.napier.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.multiplatformSettings.common)
}

kotlin {
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) iosArm64("ios")
    else iosX64("ios")

//optional if you want to mark sqldelight as resource folder
    sourceSets {
        val commonMain by getting {
            resources.srcDir("src/commonMain/sqldelight")
        }
    }
}

sqldelight {
    database("FilmSqlDatabase") {
        packageName = "com.jaa.library.domain.dataSource.storage"
    }
    linkSqlite = false
}

openApiGenerate {
    inputSpec.set(file("src/openapi.yml").path)
    generatorName.set("kotlin-ktor-client")
    additionalProperties.set(mutableMapOf("nonPublicApi" to "false"))
}

