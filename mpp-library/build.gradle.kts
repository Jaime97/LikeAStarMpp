/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mokoResources)
    plugin(Deps.Plugins.iosFramework)
}

val mppLibs = listOf(
    Deps.Libs.MultiPlatform.napier,
    Deps.Libs.MultiPlatform.mokoParcelize,
    Deps.Libs.MultiPlatform.mokoResources,
    Deps.Libs.MultiPlatform.mokoMvvm,
    Deps.Libs.MultiPlatform.multiplatformSettings,
    Deps.Libs.MultiPlatform.mokoUnits
)
val mppModules = listOf(
    Deps.Modules.domain,
    Deps.Modules.Feature.film_list,
    Deps.Modules.Feature.film_detail,
    Deps.Modules.Feature.settings
)

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines) {
        // we should force native-mt version for ktor 1.4.0 on iOS
        isForce = true
    }

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    mppLibs.forEach { commonMainApi(it.common) }
    mppModules.forEach { commonMainApi(project(it.name)) }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.jaa.library"
}

framework {
    mppModules.forEach { export(it) }
    mppLibs.forEach { export(it) }
}

// https://github.com/cashapp/sqldelight/issues/1442
kotlin.targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
    binaries.all {
        linkerOpts.add("-lsqlite3")
    }
}
