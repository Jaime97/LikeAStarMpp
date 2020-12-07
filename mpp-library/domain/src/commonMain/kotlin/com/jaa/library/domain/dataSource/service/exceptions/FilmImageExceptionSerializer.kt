package com.jaa.library.domain.dataSource.service.exceptions

import com.jaa.library.domain.entity.FilmImageBadResponse
import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptions.ErrorException
import dev.icerock.moko.network.exceptions.ResponseException
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class FilmImageExceptionSerializer(private val json: Json) : HttpExceptionFactory.HttpExceptionParser {

    override fun parseException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?
    ): ResponseException? {
        @Suppress("TooGenericExceptionCaught")
        try {
            val responseString = responseBody ?: return null
            val badResponse: FilmImageBadResponse = json.decodeFromString(FilmImageBadResponse.serializer(), responseString)
            return ErrorException(request, response, 401, badResponse.error)
        } catch (e: Exception) {
            return null
        }
    }

}