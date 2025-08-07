package com.mknishad.imovies.presentation.wishlist

import com.mknishad.imovies.domain.model.Movie

data class WishListState(
    val isLoading: Boolean = true,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)
