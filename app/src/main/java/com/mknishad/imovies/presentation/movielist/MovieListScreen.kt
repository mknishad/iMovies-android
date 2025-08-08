package com.mknishad.imovies.presentation.movielist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.presentation.components.MovieList


@Composable
fun MovieListScreen(onMovieClick: (Movie) -> Unit) {
    val viewModel = hiltViewModel<MovieListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val movies = viewModel.movies.collectAsLazyPagingItems()

    MovieListContent(movies, onMovieClick, viewModel::toggleWishlist)
}

@Composable
fun MovieListContent(
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
            // Optionally add a retry button:
            // Button(onClick = { lazyMovieItems.retry() }) { Text("Retry") }
        } else if (movies.loadState.refresh is LoadState.NotLoading && movies.itemCount == 0) {
            Text("No movies found.", modifier = Modifier.align(Alignment.Center))
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
