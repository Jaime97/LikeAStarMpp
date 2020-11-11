package com.jaa.library.domain.useCases

import com.jaa.library.domain.repository.FilmListRepository

class ChangeFavouriteStateUseCase (
    private val filmListRepository : FilmListRepository
) {

    suspend fun execute() {
        val films = filmListRepository.getFilmList()
    }

}