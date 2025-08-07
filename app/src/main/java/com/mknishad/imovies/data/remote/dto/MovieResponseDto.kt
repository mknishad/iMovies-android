package com.mknishad.imovies.data.remote.dto

data class MovieResponseDto(
    val genres: List<String>,
    val movies: List<MovieDto>
)