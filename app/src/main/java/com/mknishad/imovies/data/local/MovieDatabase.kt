package com.mknishad.imovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mknishad.imovies.data.local.entity.GenreEntity
import com.mknishad.imovies.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, GenreEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // Register TypeConverters at the database level too
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao
    abstract val genreDao: GenreDao

    companion object {
        const val DB_NAME = "movie.db"
    }
}