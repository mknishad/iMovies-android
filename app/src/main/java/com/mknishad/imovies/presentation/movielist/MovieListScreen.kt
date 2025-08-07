package com.mknishad.imovies.presentation.movielist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.presentation.movielist.components.MovieList


@Composable
fun MovieListScreen(onMovieClick: (Movie) -> Unit) {
    val viewModel = hiltViewModel<MovieListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MovieListContent(state, onMovieClick)
}

@Composable
fun MovieListContent(state: MovieListState, onMovieClick: (Movie) -> Unit) {
    MovieList(
        movies = state.movies,
        onMovieClick = { onMovieClick(it) },
        modifier = Modifier.fillMaxSize()
    )
}
