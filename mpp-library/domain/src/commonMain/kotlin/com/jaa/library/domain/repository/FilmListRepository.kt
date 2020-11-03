
package com.jaa.library.domain.repository

import dev.icerock.moko.network.generated.apis.FilmApi
import dev.icerock.moko.network.generated.models.FilmData

class FilmListRepository internal constructor(
    private val filmApi: FilmApi
) {

    internal suspend fun getFilmList():List<FilmData> {
        return filmApi.resourceWwmuGmzcJsonGet()
    }

}
