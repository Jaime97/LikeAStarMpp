package com.jaa.library.domain.useCases

import com.jaa.library.domain.coroutine.CoroutineHelper
import com.jaa.library.domain.repository.FilmListRepositoryMock
import dev.icerock.moko.network.generated.models.FilmData
import kotlin.test.Test
import kotlin.test.assertEquals

class ChangeFavouriteStateUseCaseTest {

    private val coroutineHelper:CoroutineHelper = CoroutineHelper()


    @Test
    fun testExecute() {
        val film1 = FilmData("example1", favourite = true)
        val film2 = FilmData("example2", favourite = false)
        val repository = FilmListRepositoryMock(mutableListOf(film1, film2))
        val changeFavouriteStateUseCase = ChangeFavouriteStateUseCase(repository)
        coroutineHelper.runTest {
            changeFavouriteStateUseCase.execute(
                "example1",
                object : ChangeFavouriteStateUseCase.ChangeFavouriteStateListener {
                    override fun onSuccess(filmsUpdated: List<FilmData>) {
                        val filmUpdated = filmsUpdated.find { it.title == "example1" }
                        assertEquals(filmUpdated?.favourite, false)
                        val otherFilm = filmsUpdated.find { it.title == "example2" }
                        assertEquals(otherFilm?.favourite, false)
                    }

                })
        }

    }

}