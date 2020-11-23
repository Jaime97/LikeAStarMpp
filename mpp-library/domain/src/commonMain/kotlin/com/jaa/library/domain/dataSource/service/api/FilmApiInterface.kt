package com.jaa.library.domain.dataSource.service.api

import io.ktor.client.*
import kotlinx.serialization.json.Json

interface FilmApiInterface {
    val _basePath : String
    val _httpClient : HttpClient
    val _json : Json
}