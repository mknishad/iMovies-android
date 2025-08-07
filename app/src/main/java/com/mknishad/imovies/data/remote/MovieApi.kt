package com.mknishad.imovies.data.remote

import com.mknishad.imovies.data.remote.dto.MovieResponseDto
import retrofit2.http.GET

interface MovieApi {

    @GET("/erik-sytnyk/movies-list/master/db.json")
    suspend fun getMovies(): MovieResponseDto

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com"
    }
}