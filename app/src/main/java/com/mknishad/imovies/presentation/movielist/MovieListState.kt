package com.mknishad.imovies.presentation.movielist

data class MovieListState(
    val isLoading: Boolean = true,
    val error: String = ""
)
