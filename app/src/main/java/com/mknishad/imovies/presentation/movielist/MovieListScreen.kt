package com.mknishad.imovies.presentation.movielist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mknishad.imovies.presentation.movielist.components.MovieList


@Composable
fun MovieListScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<MovieListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MovieListContent(state)
}

@Composable
fun MovieListContent(state: MovieListState, modifier: Modifier = Modifier) {
    MovieList(
        movies = state.movies,
        onMovieSelected = { }
    )
}
