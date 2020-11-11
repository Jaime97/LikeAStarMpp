package com.jaa.library.domain.dataSource.memory

import com.jaa.library.domain.dataSource.FilmDataSource
import dev.icerock.moko.network.generated.models.FilmData

class FilmMemoryStorage : FilmDataSource {

    private var films: List<FilmData> = emptyList()

    override suspend fun getFilmList(): List<FilmData> {
        return films
    }

    override suspend fun saveFilmList(films: List<FilmData>) {
        this.films = films
    }
}