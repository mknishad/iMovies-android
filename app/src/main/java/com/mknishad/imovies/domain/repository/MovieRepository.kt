package com.mknishad.imovies.domain.repository

import androidx.paging.PagingData
import com.mknishad.imovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieCount(): Int
    suspend fun getMoviesFromNetwork(): List<Movie>
    fun getMoviesFromDatabase(): Flow<PagingData<Movie>>
    fun getWishlist(): Flow<List<Movie>>
    fun getMovieById(movieId: Int): Flow<Movie?>
    suspend fun toggleFavorite(movie: Movie)
}
