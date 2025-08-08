package com.mknishad.imovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mknishad.imovies.data.local.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    // Insert genres into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Replace if genre with same ID exists
    suspend fun insertGenres(genres: List<GenreEntity>)

    // Get all genres, ordered by name
    @Query("SELECT * FROM genres ORDER BY name ASC")
    fun getAllGenres(): Flow<List<GenreEntity>>
}
