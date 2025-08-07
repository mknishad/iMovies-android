package com.mknishad.imovies.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(genres: List<String>?): String? {
        return genres?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toStringList(genresString: String?): List<String>? {
        return genresString?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, listType)
        }
    }
}