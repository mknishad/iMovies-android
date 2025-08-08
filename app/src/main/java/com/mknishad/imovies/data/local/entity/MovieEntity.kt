package com.mknishad.imovies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mknishad.imovies.data.local.Converters

@Entity(tableName = "movies")
@TypeConverters(Converters::class)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val actors: String,
    val director: String,
    val genres: List<String>,
    val plot: String,
    val posterUrl: String,
    val runtime: String,
    val title: String,
    val year: String,
    var isFavorite: Int = 0
)
