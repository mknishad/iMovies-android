package com.mknishad.imovies.presentation.splash

import com.mknishad.imovies.domain.model.Movie

data class SplashState(
    val isLoading: Boolean = true,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)