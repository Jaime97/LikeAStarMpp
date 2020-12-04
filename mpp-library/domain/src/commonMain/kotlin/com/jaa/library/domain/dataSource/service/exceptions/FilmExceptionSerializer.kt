package com.jaa.library.domain.dataSource.service.exceptions

import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptions.ErrorException
import dev.icerock.moko.network.exceptions.ResponseException
import dev.icerock.moko.network.generated.models.FilmBadResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*

class FilmExceptionSerializer(private val json: Json) : HttpExceptionFactory.HttpExceptionParser {

    override fun parseException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?
    ): ResponseException? {
        @Suppress("TooGenericExceptionCaught")
        try {
            val responseString = responseBody ?: return null
            val filmBadResponse: FilmBadResponse = json.decodeFromString(FilmBadResponse.serializer(), responseString)
            return ErrorException(request, response, 400, filmBadResponse.message)
        } catch (e: Exception) {
            return null
        }
    }
}