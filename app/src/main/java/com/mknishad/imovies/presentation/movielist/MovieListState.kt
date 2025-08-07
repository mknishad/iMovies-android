package com.mknishad.imovies.presentation.movielist

import com.mknishad.imovies.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = true,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)
