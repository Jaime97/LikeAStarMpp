package com.jaa.library.domain.repository

import com.jaa.library.domain.dataSource.memory.FilmMemoryStorage
import com.jaa.library.domain.dataSource.service.FilmService
import com.jaa.library.domain.dataSource.storage.FilmDatabase
import dev.icerock.moko.network.generated.models.FilmData

class FilmDetailRepository(
    override val filmService: FilmService,
    override val filmDatabase: FilmDatabase,
    override val filmMemoryStorage: FilmMemoryStorage
) : FilmRepository {


}