
package com.jaa.library.domain.dataSource.service.api


import com.jaa.library.domain.entity.FilmImageData
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json
import io.ktor.client.call.ReceivePipelineException
import kotlinx.serialization.builtins.serializer

class FilmImageApi(basePath: String = "http://www.omdbapi.com", httpClient: HttpClient, json: Json) {
    private val _basePath = basePath
    private val _httpClient = httpClient
    private val _json = json

    companion object {
        const val DEFAULT_APIKEY = "ee8f32ba"
    }

    /**
     *
     * This endpoint provides film data, including an image
     * @param t  (optional)
     * @param apiKey  (optional)
     * @return FilmImageData
     */
    @Suppress("UNCHECKED_CAST")
    suspend fun getFilm(t: String?, apiKey: String? = DEFAULT_APIKEY) : FilmImageData {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Get
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("")
                return@let startingPath + encodedPath.substring(1)
            }
            @Suppress("UNNECESSARY_SAFE_CALL")
            with(parameters) {
                apiKey?.let { append("apikey", it) }
                t?.let { append("t", it) }
            }
        }

        with(builder.headers) {
            append("Accept", "application/json")
        }

        try {
            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(FilmImageData.serializer(), result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }
}
