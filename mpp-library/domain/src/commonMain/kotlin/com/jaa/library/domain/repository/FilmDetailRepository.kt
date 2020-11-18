package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorage
import com.jaa.library.domain.dataSource.service.FilmService
import com.jaa.library.domain.dataSource.storage.FilmDatabase
import dev.icerock.moko.network.generated.models.FilmData

class FilmDetailRepository internal constructor(
    private val filmService: FilmService,
    private val filmDatabase: FilmDatabase,
    private val filmMemoryStorage: FilmMemoryStorage
) {

    internal fun getFilm(position:Int):FilmData {
        return filmMemoryStorage.getFilmList()[position]
    }

}