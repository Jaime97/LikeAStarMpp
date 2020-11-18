package com.jaa.library.feature.filmList.model

interface FilmRowData {
    val title: String
    val director: String
    var favourite: Boolean
    var visited: Boolean
}