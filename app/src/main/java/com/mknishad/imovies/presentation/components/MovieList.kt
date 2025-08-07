package com.mknishad.imovies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mknishad.imovies.domain.model.Movie

@Composable
fun MovieList(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp), // Space between items
        modifier = modifier
    ) {
        items(
            items = movies,
            key = { movie -> movie.id } // Important for performance and state preservation
        ) { movie ->
            MovieListItem(
                movie = movie,
                onItemClick = {
                    onMovieClick(it)
                },
                onFavoriteClick = {
                    onFavoriteClick(it)
                }
            )
        }
    }
}