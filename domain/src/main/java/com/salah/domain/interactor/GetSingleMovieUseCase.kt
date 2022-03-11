package com.salah.domain.interactor

import com.salah.domain.repository.MoviesRemoteRepository
import com.salah.model.MovieDetail
import io.reactivex.Single

class GetSingleMovieUseCase(private val moviesRemoteRepository: MoviesRemoteRepository) {

    fun execute(id: String): Single<MovieDetail> {
        return moviesRemoteRepository.getSingleMovie(id)
    }

}