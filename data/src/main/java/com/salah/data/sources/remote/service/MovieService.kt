package com.salah.data.sources.remote.service

import com.salah.data.sources.remote.model.RemoteMovieDetail
import com.salah.data.sources.remote.model.RemoteMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<RemoteMoviesResponse>

    @GET("movie/{id}")
    fun getSingleMovie(@Path("id") id: String): Single<RemoteMovieDetail>

}