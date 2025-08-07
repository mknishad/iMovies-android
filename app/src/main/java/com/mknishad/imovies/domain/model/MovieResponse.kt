package com.mknishad.imovies.domain.model

data class MovieResponse(
    val genres: List<String>,
    val movies: List<Movie>
)