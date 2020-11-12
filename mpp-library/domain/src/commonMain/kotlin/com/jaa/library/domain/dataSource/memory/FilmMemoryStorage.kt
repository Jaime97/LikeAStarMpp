package com.jaa.library.domain.dataSource.memory

import com.jaa.library.domain.dataSource.FilmDataSource
import dev.icerock.moko.network.generated.models.FilmData

class FilmMemoryStorage : FilmDataSource {

    private var films: MutableList<FilmData> = mutableListOf()

    override suspend fun getFilmList(): List<FilmData> {
        return films
    }

    override suspend fun saveFilmList(films: List<FilmData>) {
        this.films = films as MutableList<FilmData>
    }

    override suspend fun updateFilm(film: FilmData) {
        val index = films.indexOf(films.find { it.title == film.title })
        films[index] = film
    }
}