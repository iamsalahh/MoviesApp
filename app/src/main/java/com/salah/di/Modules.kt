package com.salah.di

import com.salah.data.repository.MoviesRemoteRepositoryImpl
import com.salah.data.sources.remote.mapper.MoviesRemoteMapper
import com.salah.domain.interactor.*
import com.salah.domain.repository.MoviesRemoteRepository
import com.salah.ui.details.viewmodel.MovieDetailsViewModel
import com.salah.ui.home.master.MovieListAdapter
import com.salah.ui.home.viewmodel.PopularViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MoviesRemoteMapper() }
    factory<MoviesRemoteRepository> { MoviesRemoteRepositoryImpl(get()) }
    factory { MovieListAdapter(androidContext()) }
}

val popularMoviesModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    viewModel { PopularViewModel(get()) }
}


val movieDetailsModule = module {
    factory { GetSingleMovieUseCase(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}