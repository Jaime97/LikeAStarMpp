package com.jaa.library.domain.dataSource.storage

import com.jaa.library.domain.dataSource.FilmDataSource
import com.jaa.library.domain.storage.FilmDb
import dev.icerock.moko.network.generated.models.FilmData

class FilmDatabase(
    private val filmSqlDatabase: FilmSqlDatabase
) : FilmDataSource {

    override suspend fun getFilmList():List<FilmData> {
        return filmSqlDatabase.filmSqlDatabaseQueries.selectAllFilms().executeAsList().map { it.toFilmData() }
    }

    override suspend fun saveFilmList(films: List<FilmData>) {
        for(film in films) {
            filmSqlDatabase.filmSqlDatabaseQueries.insertFilm(film.toFilmDb())
        }
    }

    override suspend fun updateFilm(film: FilmData) {
        filmSqlDatabase.filmSqlDatabaseQueries.insertFilm(film.toFilmDb())
    }

    private fun FilmDb.toFilmData() : FilmData {
        return FilmData(title, release_year?.toInt(), locations, fun_facts, production_company, distributor, director, writer, actor_1, actor_2, actor_3, visited, favourite)
    }

    private fun FilmData.toFilmDb() : FilmDb {
        return FilmDb(title, actor1, actor2, actor3, director, releaseYear?.toLong(), locations, funFacts, productionCompany, distributor, writer, favourite?:false, visited?:false)
    }
}

