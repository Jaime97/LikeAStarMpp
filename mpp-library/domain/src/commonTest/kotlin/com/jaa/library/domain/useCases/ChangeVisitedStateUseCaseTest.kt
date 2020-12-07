package com.jaa.library.domain.useCases

import com.jaa.library.domain.coroutine.CoroutineHelper
import com.jaa.library.domain.repository.FilmDetailRepositoryMock
import com.jaa.library.domain.repository.FilmListRepositoryMock
import dev.icerock.moko.network.generated.models.FilmData
import kotlin.test.*

class ChangeVisitedStateUseCaseTest {

    private val coroutineHelper:CoroutineHelper = CoroutineHelper()

    @Test
    fun changeVisitedStateNormal() {
        val film1 = FilmData("example1", visited = true)
        val film2 = FilmData("example2", visited = false)
        val repository = FilmDetailRepositoryMock(mutableListOf(film1, film2))
        val changeVisitedStateUseCase = ChangeVisitedStateUseCase(repository)
        coroutineHelper.runTest {
            changeVisitedStateUseCase.execute(
                "example1",
                object : ChangeVisitedStateUseCase.ChangeVisitedStateListener {
                    override fun onSuccess(filmUpdated: FilmData) {
                        assertEquals(filmUpdated.visited, false)
                        val otherFilm = repository.getFilmList().find { it.title == "example2" }
                        assertEquals(otherFilm?.visited, false)
                        assertEquals(repository.getFilmList().size, 2)
                    }

                    override fun onError() {
                        fail()
                    }

                })
        }
    }

    @Test
    fun changeVisitedStateWithTitleNotInList() {
        val film1 = FilmData("example1", visited = true)
        val film2 = FilmData("example2", visited = false)
        val repository = FilmDetailRepositoryMock(mutableListOf(film1, film2))
        val changeVisitedStateUseCase = ChangeVisitedStateUseCase(repository)
        coroutineHelper.runTest {
            changeVisitedStateUseCase.execute(
                "example3",
                object : ChangeVisitedStateUseCase.ChangeVisitedStateListener {
                    override fun onSuccess(filmUpdated: FilmData) {
                        fail()
                    }

                    override fun onError() {
                        val films = repository.getFilmList()
                        val filmUpdated = films.find { it.title == "example1" }
                        assertEquals(filmUpdated?.visited, true)
                        val otherFilm = films.find { it.title == "example2" }
                        assertEquals(otherFilm?.visited, false)
                        val nullFilm = films.find { it.title == "example3" }
                        assertNull(nullFilm)
                    }
                })
        }
    }

    @Test
    fun changeVisitedStateWithEmptyTitle() {
        val film1 = FilmData("example1", visited = true)
        val film2 = FilmData("example2", visited = false)
        val repository = FilmDetailRepositoryMock(mutableListOf(film1, film2))
        val changeVisitedStateUseCase = ChangeVisitedStateUseCase(repository)
        coroutineHelper.runTest {
            changeVisitedStateUseCase.execute(
                "",
                object : ChangeVisitedStateUseCase.ChangeVisitedStateListener {
                    override fun onSuccess(filmUpdated: FilmData) {
                        fail()
                    }

                    override fun onError() {
                        val films = repository.getFilmList()
                        val filmUpdated = films.find { it.title == "example1" }
                        assertEquals(filmUpdated?.visited, true)
                        val otherFilm = films.find { it.title == "example2" }
                        assertEquals(otherFilm?.visited, false)
                        val nullFilm = films.find { it.title == "" }
                        assertNull(nullFilm)
                    }
                })
        }
    }

}