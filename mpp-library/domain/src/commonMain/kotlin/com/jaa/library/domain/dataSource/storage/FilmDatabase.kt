package com.jaa.library.domain.dataSource.storage

import com.jaa.library.domain.storage.FilmDb
import com.jaa.library.domain.storage.ListOffsetMapper
import dev.icerock.moko.network.generated.models.FilmData

class FilmDatabase(
    private val filmSqlDatabase: FilmSqlDatabase?
) {

    fun getFilmListWithLocalOffset(offset: Int, limit: Int): List<FilmData> {
        //return filmSqlDatabase.filmSqlDatabaseQueries.selectFilmsWithOffset(offset.toLong(), limit.toLong()).executeAsList().map { it.toFilmData() }
        return emptyList()
    }

    fun getFilmListWithOnlineOffset(offset: Int, limit: Int): List<FilmData> {/*
        val listOfOffsets = filmSqlDatabase.filmSqlDatabaseQueries.selectListOffsetMapperWithOffset(limit.toLong(), offset.toLong()).executeAsList()
        return if(listOfOffsets.isNotEmpty()) {
            filmSqlDatabase.filmSqlDatabaseQueries.selectFilmsWithOffset(listOfOffsets.last().local_offset, listOfOffsets[0].local_offset).executeAsList().map { it.toFilmData() }
        } else {
            emptyList()
        }
        */
        return emptyList()
    }

    fun saveFilmList(films: List<FilmData>) {/*
        if(films.isNotEmpty()) {
            val maxOfflineOffset = filmSqlDatabase.filmSqlDatabaseQueries.selectMaxOfflineOffset().executeAsOne()
            for ((currentOfflineOffset, film) in films.withIndex()) {
                val maxOnlineOffset = filmSqlDatabase.filmSqlDatabaseQueries.selectMaxOnlineOffset().executeAsOne()
                for(i in 0..(film.numberOfLocations?:0)) {
                    filmSqlDatabase.filmSqlDatabaseQueries.insertOffsetMapper(ListOffsetMapper((maxOnlineOffset.MAX?:0) + i, (maxOfflineOffset.MAX?:0) + currentOfflineOffset))
                }
                filmSqlDatabase.filmSqlDatabaseQueries.insertFilm(film.toFilmDb())
            }
        }*/
    }

    fun updateFilm(film: FilmData) {
        //filmSqlDatabase.filmSqlDatabaseQueries.insertFilm(film.toFilmDb())
    }

    private fun FilmDb.toFilmData() : FilmData {
        return FilmData(title, release_year?.toInt(), locations, fun_facts, production_company, distributor, director, writer, actor_1, actor_2, actor_3, visited, favourite)
    }

    private fun FilmData.toFilmDb() : FilmDb {
        return FilmDb(title, actor1, actor2, actor3, director, releaseYear?.toLong(), locations, funFacts, productionCompany, distributor, writer, favourite?:false, visited?:false, numberOfLocations?.toLong())
    }
}

