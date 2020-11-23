
package com.jaa.library.domain.di

import com.github.aakira.napier.Napier
import com.jaa.library.domain.dataSource.memory.FilmMemoryStorage
import com.jaa.library.domain.dataSource.service.FilmImageService
import com.jaa.library.domain.repository.FilmListRepository
import com.jaa.library.domain.dataSource.service.FilmService
import com.jaa.library.domain.dataSource.service.api.FilmImageApi
import com.jaa.library.domain.dataSource.storage.FilmDatabase
import com.jaa.library.domain.dataSource.storage.FilmSqlDatabase
import com.jaa.library.domain.repository.FilmDetailRepository
import com.squareup.sqldelight.db.SqlDriver
import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptionfactory.parser.ErrorExceptionParser
import dev.icerock.moko.network.exceptionfactory.parser.ValidationExceptionParser
import dev.icerock.moko.network.features.ExceptionFeature
import dev.icerock.moko.network.generated.apis.FilmApi
import io.ktor.client.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class DomainFactory(
    private val baseFilmUrl: String,
    private val baseFilmImageUrl:String,
    private val sqlDriver: SqlDriver
) {
    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    private val httpClient: HttpClient by lazy {
        HttpClient {
            install(ExceptionFeature) {
                exceptionFactory = HttpExceptionFactory(
                    defaultParser = ErrorExceptionParser(json),
                    customParsers = mapOf(
                        HttpStatusCode.UnprocessableEntity.value to ValidationExceptionParser(json)
                    )
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message = message)
                    }
                }
                level = LogLevel.ALL
            }

            // disable standard BadResponseStatus - exceptionfactory do it for us
            expectSuccess = false
        }
    }

    private val filmApi: FilmApi by lazy {
        FilmApi(baseFilmUrl, httpClient, json)
    }

    private val filmService: FilmService by lazy {
        FilmService(filmApi)
    }

    private val filmImageApi: FilmImageApi by lazy {
        FilmImageApi(baseFilmImageUrl, httpClient, json)
    }

    private val filmImageService: FilmImageService by lazy {
        FilmImageService(filmImageApi)
    }

    private val filmSqlDatabase: FilmSqlDatabase by lazy {
        FilmSqlDatabase(sqlDriver)
    }

    private val filmDatabase: FilmDatabase by lazy {
        FilmDatabase(filmSqlDatabase)
    }

    private val filmMemoryStorage: FilmMemoryStorage by lazy {
        FilmMemoryStorage()
    }

    val filmListRepository: FilmListRepository by lazy {
        FilmListRepository(filmService, filmDatabase, filmMemoryStorage)
    }

    val filmDetailRepository: FilmDetailRepository by lazy {
        FilmDetailRepository(filmImageService, filmDatabase, filmMemoryStorage)
    }
}