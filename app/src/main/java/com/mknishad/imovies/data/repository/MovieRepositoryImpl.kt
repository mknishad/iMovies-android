package com.mknishad.imovies.data.repository

import com.mknishad.imovies.data.mappers.toMovieResponse
import com.mknishad.imovies.data.remote.MovieApi
import com.mknishad.imovies.domain.model.MovieResponse
import com.mknishad.imovies.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {
    override suspend fun getMovies(): MovieResponse {
        return api.getMovies().toMovieResponse()
    }
}
