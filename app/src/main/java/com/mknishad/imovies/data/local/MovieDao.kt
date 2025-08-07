package com.mknishad.imovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mknishad.imovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Insert movies into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Replace if movie with same ID exists
    suspend fun insertMovies(movies: List<MovieEntity>)

    // Get movie count to quickly check if DB is empty
    @Query("SELECT COUNT(*) FROM movieentity")
    suspend fun getMovieCount(): Int

    // Get all movies, ordered by year and title
    @Query("SELECT * FROM movieentity ORDER BY year DESC, title ASC")
    fun getAllMovies(): Flow<List<MovieEntity>> // Flow for reactive updates

    // Get favorite movies, ordered by year and title
    @Query("SELECT * FROM movieentity WHERE isFavorite = 1 ORDER BY year DESC, title ASC")
    fun getWishlist(): Flow<List<MovieEntity>> // Flow for reactive updates

    // Get movie by ID
    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    // Add to / remove from the wishlist
    @Update
    suspend fun updateMovie(movie: MovieEntity)

}
