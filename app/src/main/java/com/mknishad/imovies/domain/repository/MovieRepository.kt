package com.mknishad.imovies.domain.repository

import com.mknishad.imovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesFromDatabase(): Flow<List<Movie>>
    suspend fun getMoviesFromNetwork(): List<Movie>
}
