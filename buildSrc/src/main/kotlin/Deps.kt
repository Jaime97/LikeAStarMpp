/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */


object Deps {
    private const val kotlinVersion = "1.4.10"

    private const val materialVersion = "1.2.1"
    private const val recyclerViewVersion = "1.1.0"
    private const val swipeRefreshLayoutVersion = "1.1.0"
    private const val picassoVersion = "2.71828"
    private const val lifecycleVersion = "2.2.0"
    private const val androidAppCompatVersion = "1.1.0"
    private const val espressoCoreVersion = "3.2.0"
    private const val testRunnerVersion = "1.2.0"
    private const val testExtJunitVersion = "1.1.1"

    private const val kotlinxSerializationVersion = "1.0.0-RC"
    private const val coroutinesVersion = "1.3.9-native-mt-2"
    private const val ktorClientVersion = "1.4.0"
    private const val sqlDelightVersion = "1.4.4"

    private const val detektVersion = "1.12.0"

    private const val mokoParcelizeVersion = "0.4.0"
    private const val mokoResourcesVersion = "0.13.1"
    private const val mokoMvvmVersion = "0.8.0"
    private const val mokoNetworkVersion = "0.8.0"
    private const val mokoUnitsVersion = "0.4.1"
    private const val mokoPermissionsVersion = "0.7.0"
    private const val multiplatformSettingsVersion = "0.6.3"

    private const val napierVersion = "1.4.1"

    object Android {
        const val compileSdk = 29
        const val targetSdk = 29
        const val minSdk = 21
    }

    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val androidLibrary = GradlePlugin(id = "com.android.library")
        val kotlinMultiplatform = GradlePlugin(
            id = "org.jetbrains.kotlin.multiplatform",
            module = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        )
        val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val kotlinAndroidExtensions = GradlePlugin(id = "kotlin-android-extensions")
        val kotlinSerialization = GradlePlugin(
            id = "org.jetbrains.kotlin.plugin.serialization",
            module = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
        )
        val sqlDelight = GradlePlugin(
            id = "com.squareup.sqldelight",
            module = "com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")

        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")

        val mokoNetwork = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-network-generator",
            module = "dev.icerock.moko:network-generator:$mokoNetworkVersion"
        )
        val mokoResources = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-resources",
            module = "dev.icerock.moko:resources-generator:$mokoResourcesVersion"
        )
        val mokoUnits = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-units",
            module = "dev.icerock.moko:units-generator:$mokoUnitsVersion"
        )

        val detekt = GradlePlugin(
            id = "io.gitlab.arturbosch.detekt",
            version = detektVersion
        )
    }

    object Libs {

        object Android {
            const val appCompat =
                "androidx.appcompat:appcompat:$androidAppCompatVersion"
            const val material =
                "com.google.android.material:material:$materialVersion"
            const val recyclerView =
                "androidx.recyclerview:recyclerview:$recyclerViewVersion"
            const val swipeRefreshLayout =
                "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayoutVersion"
            const val sqlDelightDriver =
                "com.squareup.sqldelight:android-driver:$sqlDelightVersion"
            const val picasso =
                "com.squareup.picasso:picasso:$picassoVersion"
            val lifecycle =
                "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"

            object Tests {
                const val espressoCore =
                    "androidx.test.espresso:espresso-core:$espressoCoreVersion"
                const val kotlinTestJUnit =
                    "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion"
                const val testCore =
                    "androidx.test:core:1.3.0"
                const val robolectric =
                    "org.robolectric:robolectric:4.3"
                const val testRunner =
                    "androidx.test:runner:$testRunnerVersion"
                const val testRules =
                    "androidx.test:rules:$testRunnerVersion"
                const val testExtJunit =
                    "androidx.test.ext:junit:$testExtJunitVersion"
            }
        }

        object MultiPlatform {
            const val kotlinSerialization =
                "org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion"
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val ktorClient =
                "io.ktor:ktor-client-core:$ktorClientVersion"
            const val ktorClientLogging =
                "io.ktor:ktor-client-logging:$ktorClientVersion"
            val mokoResources =
                "dev.icerock.moko:resources:$mokoResourcesVersion".mpl
            val mokoParcelize =
                "dev.icerock.moko:parcelize:$mokoParcelizeVersion".mpl
            val mokoMvvm =
                "dev.icerock.moko:mvvm:$mokoMvvmVersion".mpl
            val mokoNetwork =
                "dev.icerock.moko:network:$mokoNetworkVersion".mpl
            val mokoPermissions =
                "dev.icerock.moko:permissions:$mokoPermissionsVersion".mpl
            val mokoUnits =
                "dev.icerock.moko:units:$mokoUnitsVersion".mpl
            val multiplatformSettings =
                "com.russhwolf:multiplatform-settings-no-arg:$multiplatformSettingsVersion".mpl
            val napier =
                "com.github.aakira:napier:$napierVersion".mpl
            val sqlDelight = MultiPlatformLibrary(
                common = "com.squareup.sqldelight:runtime:$sqlDelightVersion",
                android = "com.squareup.sqldelight:android-driver:$sqlDelightVersion",
                ios = "com.squareup.sqldelight:native-driver:$sqlDelightVersion"
            )

            object Tests {
                const val kotlinTest =
                    "org.jetbrains.kotlin:kotlin-test-common:$kotlinVersion"
                const val kotlinTestAnnotations =
                    "org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlinVersion"
            }
        }

        object Detekt {
            const val detektFormatting =
                "io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion"
        }
    }

    object Modules {
        val domain = MultiPlatformModule(
            name = ":mpp-library:domain",
            exported = true
        )

        object Feature {
            val film_list = MultiPlatformModule(
                name = ":mpp-library:feature:film_list",
                exported = true
            )
            val film_detail = MultiPlatformModule(
                name = ":mpp-library:feature:film_detail",
                exported = true
            )
            val settings = MultiPlatformModule(
                name = ":mpp-library:feature:settings",
                exported = true
            )
        }
    }

    private val String.mpl: MultiPlatformLibrary
        get() = MultiPlatformLibrary(
            common = this,
            iosX64 = this.replace(Regex("(.*):(.*):(.*)"), "$1:$2-iosx64:$3"),
            iosArm64 = this.replace(Regex("(.*):(.*):(.*)"), "$1:$2-iosarm64:$3")
        )
}
