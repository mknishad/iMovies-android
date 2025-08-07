package com.mknishad.imovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mknishad.imovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Replace if movie with same ID exists
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT COUNT(*) FROM movieentity") // To quickly check if DB is empty
    suspend fun getMovieCount(): Int

    @Query("SELECT * FROM movieentity ORDER BY year DESC, title ASC")
    fun getAllMovies(): Flow<List<MovieEntity>> // Flow for reactive updates

    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?
}