package com.mknishad.imovies.presentation.movielist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mknishad.imovies.domain.model.Movie

@Composable
fun MovieList(movies: List<Movie>, onMovieSelected: (Movie) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Space between items
    ) {
        items(
            items = movies,
            key = { movie -> movie.id } // Important for performance and state preservation
        ) { movie ->
            MovieListItem(
                movie = movie,
                onItemClick = { selectedMovie ->
                    onMovieSelected(selectedMovie)
                }
            )
        }
    }
}