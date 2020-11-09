
package com.jaa.library.domain.di

import com.github.aakira.napier.Napier
import com.jaa.library.domain.repository.FilmListRepository
import com.jaa.library.domain.service.FilmService
import com.jaa.library.domain.storage.FilmDatabase
import com.jaa.library.domain.storage.FilmSqlDatabase
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
    private val baseUrl: String,
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
                level = LogLevel.HEADERS
            }

            // disable standard BadResponseStatus - exceptionfactory do it for us
            expectSuccess = false
        }
    }

    private val filmApi: FilmApi by lazy {
        FilmApi(baseUrl, httpClient, json)
    }

    private val filmService: FilmService by lazy {
        FilmService(filmApi)
    }

    private val filmSqlDatabase: FilmSqlDatabase by lazy {
        FilmSqlDatabase(sqlDriver)
    }

    private val filmDatabase: FilmDatabase by lazy {
        FilmDatabase(filmSqlDatabase)
    }

    val filmListRepository: FilmListRepository by lazy {
        FilmListRepository(filmService, filmDatabase)
    }
}