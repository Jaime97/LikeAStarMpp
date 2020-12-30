package com.jaa.library.feature.filmDetail.model

interface FilmDetail {
    val title: String
    val director: String
    val locations: String
    val productionCompany:String
    val actor1:String
    var visited: Boolean
}