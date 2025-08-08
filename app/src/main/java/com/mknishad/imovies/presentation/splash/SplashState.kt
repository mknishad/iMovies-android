package com.mknishad.imovies.presentation.splash

data class SplashState(
    val isLoading: Boolean = true,
    val isLoadFinished: Boolean = false,
    val error: String = ""
)