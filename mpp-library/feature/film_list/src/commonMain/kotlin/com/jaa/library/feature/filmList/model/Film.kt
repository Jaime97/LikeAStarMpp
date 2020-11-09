package com.jaa.library.feature.filmList.model

data class Film(
    val title: String,
    val actor_1: String,
    val director: String,
    val locations: String,
    val production_company: String,
    var favourite: Boolean = false,
    var visited: Boolean = false
)