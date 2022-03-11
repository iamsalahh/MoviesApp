package com.salah.domain.interactor

import com.salah.domain.repository.MoviesRemoteRepository
import com.salah.model.MoviesResponse
import io.reactivex.Single

class GetPopularMoviesUseCase(private val moviesRemoteRepository: MoviesRemoteRepository) {

    fun execute(page: Int): Single<MoviesResponse> {
        return moviesRemoteRepository.getPopularMovies(page)
    }

}