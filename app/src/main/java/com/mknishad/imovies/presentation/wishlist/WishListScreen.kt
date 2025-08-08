package com.mknishad.imovies.presentation.wishlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mknishad.imovies.domain.model.Movie


@Composable
fun WishListScreen(onMovieClick: (Movie) -> Unit) {
    val viewModel = hiltViewModel<WishListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    WishListContent(state, onMovieClick, viewModel::toggleWishlist)
}

@Composable
fun WishListContent(
    state: WishListState,
    onMovieClick: (Movie) -> Unit,
    onFavoriteClick: (Movie) -> Unit
) {
    /*MovieList(
        movies = state.movies,
        onMovieClick = { onMovieClick(it) },
        onFavoriteClick = { onFavoriteClick(it) },
        modifier = Modifier.fillMaxSize()
    )*/
}
