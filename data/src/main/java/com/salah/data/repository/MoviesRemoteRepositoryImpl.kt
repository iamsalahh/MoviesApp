package com.salah.data.repository

import com.salah.data.sources.remote.api.ApiClient
import com.salah.data.sources.remote.mapper.MoviesRemoteMapper
import com.salah.domain.repository.MoviesRemoteRepository
import com.salah.model.MovieDetail
import com.salah.model.MoviesResponse
import io.reactivex.Single

class MoviesRemoteRepositoryImpl(private val moviesRemoteMapper: MoviesRemoteMapper) : MoviesRemoteRepository  {

    override fun getPopularMovies(page: Int): Single<MoviesResponse> {
        return ApiClient.movieService().getPopularMovies(page).map {
            moviesRemoteMapper.mapFromRemote(it)
        }
    }
    override fun getSingleMovie(id: String): Single<MovieDetail> {
        return ApiClient.movieService().getSingleMovie(id).map {
            moviesRemoteMapper.mapDetailFromRemote(it)
        }
    }

}