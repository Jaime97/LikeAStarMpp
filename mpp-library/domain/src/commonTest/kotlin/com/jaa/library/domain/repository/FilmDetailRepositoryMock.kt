package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorageInterface
import com.jaa.library.domain.dataSource.memory.FilmMemoryStorageMock
import com.jaa.library.domain.dataSource.storage.FilmDatabaseInterface
import com.jaa.library.domain.dataSource.storage.FilmDatabaseMock
import com.jaa.library.domain.repository.filmDetail.FilmDetailRepositoryInterface
import com.jaa.library.domain.useCases.GetFilmImageUseCase
import dev.icerock.moko.network.generated.models.FilmData

class FilmDetailRepositoryMock(
    private val filmList: MutableList<FilmData>
) : FilmDetailRepositoryInterface {

    override val filmDatabase: FilmDatabaseInterface = FilmDatabaseMock()
    override val filmMemoryStorage: FilmMemoryStorageInterface = FilmMemoryStorageMock()

    override suspend fun getImageUrlOfFilm(
        title: String,
        listener: GetFilmImageUseCase.GetFilmImageListener
    ) {

    }

    override fun getFilm(title: String): FilmData? {
        return try {filmList.first { it.title == title }} catch (e:NoSuchElementException) { null }
    }

    override fun updateFilm(film: FilmData) {
        val index = filmList.indexOf(filmList.find { it.title == film.title })
        filmList[index] = film
    }

    fun getFilmList(): List<FilmData> {
        return filmList
    }
}