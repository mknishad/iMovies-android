package com.mknishad.imovies.presentation.moviedetails

import com.mknishad.imovies.domain.model.Movie

data class MovieDetailsState(
    val isLoading: Boolean = true,
    val movie: Movie? = null,
    val error: String = ""
)
