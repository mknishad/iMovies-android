package com.mknishad.imovies.domain.repository

import com.mknishad.imovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMoviesFromNetwork(): List<Movie>
    fun getMoviesFromDatabase(): Flow<List<Movie>>
    fun getWishlist(): Flow<List<Movie>>
    fun getMovieById(movieId: Int): Flow<Movie?>
    suspend fun toggleFavorite(movie: Movie)
}
