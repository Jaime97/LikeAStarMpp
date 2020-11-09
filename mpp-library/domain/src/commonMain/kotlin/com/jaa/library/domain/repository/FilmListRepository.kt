
package com.jaa.library.domain.repository

import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepository internal constructor(
    private val filmService: FilmDataSource,
    private val filmDatabase: FilmDataSource
) {

    internal suspend fun getFilmList():List<FilmData> {
        val databaseFilms = filmDatabase.getFilmList()
        return if(databaseFilms.isNotEmpty()) databaseFilms else downloadFilmList()
    }

    private suspend fun downloadFilmList():List<FilmData> {
        val films = filmService.getFilmList()
        if(films.isNotEmpty()) {
            filmDatabase.saveFilmList(films)
        }
        return films
    }

}
