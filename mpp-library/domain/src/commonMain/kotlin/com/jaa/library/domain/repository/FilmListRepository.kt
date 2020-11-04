
package com.jaa.library.domain.repository

import com.jaa.library.domain.service.FilmService
import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepository internal constructor(
    private val filmService: FilmService
) {

    internal suspend fun getFilmList():List<FilmData> {
        return filmService.getFilmList()
    }

}
