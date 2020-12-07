package com.jaa.library.domain.dataSource.memory

import dev.icerock.moko.network.generated.models.FilmData

interface FilmMemoryStorageInterface {

    fun getFilmList(): List<FilmData>

    fun getFilmListWithOffset(offset: Int): List<FilmData>

    fun saveFilmList(films: List<FilmData>)

    fun updateFilm(film: FilmData)

}