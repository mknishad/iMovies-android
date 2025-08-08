package com.mknishad.imovies.presentation.wishlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mknishad.imovies.R
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.presentation.components.MovieList


@Composable
fun WishListScreen(onMovieClick: (Movie) -> Unit) {
    val viewModel = hiltViewModel<WishListViewModel>()
    val movies = viewModel.movies.collectAsLazyPagingItems()

    WishListContent(movies, onMovieClick, viewModel::toggleWishlist)
}

@Composable
fun WishListContent(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit,
    onFavoriteClick: (Movie) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (movies.loadState.refresh is LoadState.Loading && movies.itemCount == 0) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (movies.loadState.refresh is LoadState.Error && movies.itemCount == 0) {
            val error = (movies.loadState.refresh as LoadState.Error).error
            Text(
                text = "Error: ${error.localizedMessage}",
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.error
            )
        } else if (movies.loadState.refresh is LoadState.NotLoading && movies.itemCount == 0) {
            Text(stringResource(R.string.no_movies_found), modifier = Modifier.align(Alignment.Center))
        } else {
            MovieList(
                movies = movies,
                onMovieClick = { onMovieClick(it) },
                onFavoriteClick = { onFavoriteClick(it) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
