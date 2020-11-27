package com.jaa.library.domain.dataSource.memory

import dev.icerock.moko.network.generated.models.FilmData

class FilmMemoryStorage {

    private var films: MutableList<FilmData> = mutableListOf()

    fun getFilmList(): List<FilmData> {
        return films
    }

    fun getFilmListWithOffset(offset: Int): List<FilmData> {
        return if(films.lastIndex > offset)films else emptyList()
    }

    fun saveFilmList(films: List<FilmData>) {
        if(films.isNotEmpty()) {
            this.films = (this.films + films).distinctBy { it.title } as MutableList<FilmData>
        }
    }

    fun updateFilm(film: FilmData) {
        val index = films.indexOf(films.find { it.title == film.title })
        films[index] = film
    }
}