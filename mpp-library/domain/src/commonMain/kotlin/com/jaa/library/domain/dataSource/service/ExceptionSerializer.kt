package com.jaa.library.domain.dataSource.service

import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptions.ErrorException
import dev.icerock.moko.network.exceptions.ResponseException
import dev.icerock.moko.network.generated.models.BadResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*

class ExceptionSerializer(private val json: Json) : HttpExceptionFactory.HttpExceptionParser {

    override fun parseException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?
    ): ResponseException? {
        @Suppress("TooGenericExceptionCaught")
        try {
            val responseString = responseBody ?: return null
            val badResponse: BadResponse = json.decodeFromString(BadResponse.serializer(), responseString)
            return ErrorException(request, response, 400, badResponse.message)
        } catch (e: Exception) {
            return null
        }
    }
}