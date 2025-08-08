package com.mknishad.imovies.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.mknishad.imovies.R
import com.mknishad.imovies.domain.model.Movie

@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>,
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
            count = movies.itemCount,
            key = movies.itemKey { movie -> movie.id } // Important for performance and state preservation
        ) { index ->
            val movie = movies[index]
            if (movie != null) {
                MovieListItem(
                    movie = movie,
                    onItemClick = {
                        onMovieClick(it)
                    },
                    onFavoriteClick = {
                        onFavoriteClick(it)
                    },
                    modifier = Modifier.animateItem(
                        placementSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )
                )
            }
        }

        // Handle loading state for appending more items
        movies.loadState.append.let { appendState ->
            when (appendState) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(32.dp))
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                stringResource(R.string.error_loading_more),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                is LoadState.NotLoading -> Unit
            }
        }
    }
}
