package com.mknishad.imovies.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mknishad.imovies.data.local.entity.MovieEntity
import com.mknishad.imovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Insert movies into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Replace if movie with same ID exists
    suspend fun insertMovies(movies: List<MovieEntity>)

    // Get movie count to quickly check if DB is empty
    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    // Get all movies, ordered by year and title
    @Query("SELECT * FROM movies ORDER BY year DESC, title ASC")
    fun getAllMovies(): PagingSource<Int, Movie>

    // Get movies by genre, ordered by year and title
    @Query("SELECT * FROM movies WHERE genres LIKE :genreQuery ORDER BY year DESC, title ASC")
    fun getMoviesByGenre(genreQuery: String): PagingSource<Int, Movie>

    // Get favorite movies, ordered by year and title
    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY year DESC, title ASC")
    fun getWishlist(): PagingSource<Int, Movie>

    // Get movie by ID
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity?>

    // Add to / remove from the wishlist
    @Update
    suspend fun updateMovie(movie: MovieEntity)
}
