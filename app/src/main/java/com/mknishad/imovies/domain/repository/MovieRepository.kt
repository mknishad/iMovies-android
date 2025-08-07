package com.mknishad.imovies.domain.repository

import com.mknishad.imovies.domain.model.MovieResponse

interface MovieRepository {
    suspend fun getMovies() : MovieResponse
}
