package com.salah.domain.repository

import com.salah.model.MovieDetail
import com.salah.model.MoviesResponse
import io.reactivex.Single

interface MoviesRemoteRepository {

    fun getPopularMovies(page: Int): Single<MoviesResponse>

    fun getSingleMovie(id: String): Single<MovieDetail>
}