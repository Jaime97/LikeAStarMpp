package com.jaa.library.domain.dataSource.memory

import dev.icerock.moko.network.generated.models.FilmData

class FilmMemoryStorage : FilmMemoryStorageInterface {

    private var films: MutableList<FilmData> = mutableListOf()

    override fun getFilmList(): List<FilmData> {
        return films
    }

    override fun getFilmListWithOffset(offset: Int): List<FilmData> {
        return if(films.lastIndex > offset)films else emptyList()
    }

    override fun saveFilmList(films: List<FilmData>) {
        if(films.isNotEmpty()) {
            this.films = (this.films + films).distinctBy { it.title } as MutableList<FilmData>
        }
    }

    override fun updateFilm(film: FilmData) {
        val index = films.indexOf(films.find { it.title == film.title })
        films[index] = film
    }
}