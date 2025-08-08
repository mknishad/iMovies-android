package com.mknishad.imovies.presentation.wishlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.mknishad.imovies.presentation.main.Screen


@Composable
fun WishlistScreen(onMovieClick: (Movie) -> Unit, onNavigateBack: () -> Unit) {
    val viewModel = hiltViewModel<WishlistViewModel>()
    val movies = viewModel.movies.collectAsLazyPagingItems()

    WishlistContent(
        movies = movies,
        onMovieClick = onMovieClick,
        onFavoriteClick = viewModel::toggleWishlist,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistContent(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Screen.Wishlist.title))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(MaterialTheme.colorScheme.primaryContainer),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
                Text(
                    stringResource(R.string.no_movies_found),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (movies.itemCount > 0) {
                MovieList(
                    movies = movies,
                    onMovieClick = { onMovieClick(it) },
                    onFavoriteClick = { onFavoriteClick(it) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
