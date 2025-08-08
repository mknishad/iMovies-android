package com.mknishad.imovies.presentation.movielist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mknishad.imovies.R
import com.mknishad.imovies.domain.model.Genre
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.presentation.components.MovieList
import com.mknishad.imovies.presentation.main.Screen
import com.mknishad.imovies.presentation.movielist.components.GenreDropdownAction


@Composable
fun MovieListScreen(onMovieClick: (Movie) -> Unit, onWishlistClick: () -> Unit) {
    val viewModel = hiltViewModel<MovieListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val movies = state.movies.collectAsLazyPagingItems()

    MovieListContent(
        movies = movies,
        onMovieClick = onMovieClick,
        onWishlistClick = onWishlistClick,
        onFavoriteClick = viewModel::toggleWishlist,
        availableGenres = state.availableGenres,
        selectedGenre = state.selectedGenre,
        isExpanded = state.isDropdownExpanded,
        onToggleDropdown = viewModel::toggleGenreDropdown,
        onGenreSelected = viewModel::onGenreSelected,
        onDismiss = viewModel::dismissGenreDropdown,
        isSearchActive = state.isSearchActive,
        searchQuery = state.searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onToggleSearch = viewModel::onToggleSearch,
        wishlistCount = state.wishlistCount
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListContent(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit,
    onWishlistClick: () -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    availableGenres: List<Genre>,
    selectedGenre: Genre?,
    isExpanded: Boolean,
    onToggleDropdown: () -> Unit,
    onGenreSelected: (Genre) -> Unit,
    onDismiss: () -> Unit,
    isSearchActive: Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onToggleSearch: () -> Unit,
    wishlistCount: Int
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearchActive) {
        if (isSearchActive) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Screen.MovieList.title))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(MaterialTheme.colorScheme.primaryContainer),
                actions = {
                    AnimatedVisibility(
                        visible = isSearchActive,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { onSearchQueryChanged(it) },
                            modifier = Modifier
                                .weight(1f) // Takes available space
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                .focusRequester(focusRequester),
                            placeholder = { Text("Search movies...") },
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search Icon"
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { onSearchQueryChanged("") }) {
                                        Icon(
                                            Icons.Filled.Close,
                                            contentDescription = "Clear search"
                                        )
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                keyboardController?.hide()
                                // Optionally, trigger search immediately or rely on debounce
                            }),
                            /*colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Transparent, // Or your theme color
                                unfocusedBorderColor = Color.Transparent
                            )*/
                        )
                    }

                    // Search Toggle Button
                    IconButton(onClick = { onToggleSearch() }) {
                        Icon(
                            imageVector = if (isSearchActive) Icons.Filled.Close else Icons.Filled.Search,
                            contentDescription = if (isSearchActive) {
                                stringResource(R.string.close_search)
                            } else {
                                stringResource(R.string.open_search)
                            }
                        )
                    }
                    GenreDropdownAction(
                        availableGenres = availableGenres,
                        selectedGenre = selectedGenre,
                        isExpanded = isExpanded,
                        onToggleDropdown = onToggleDropdown,
                        onGenreSelected = onGenreSelected,
                        onDismiss = onDismiss
                    )
                    IconButton(
                        onClick = { onWishlistClick() },
                        modifier = Modifier
                            .size(56.dp)
                            .padding(end = 8.dp)
                    ) {
                        BadgedBox(badge = {
                            if (wishlistCount > 0) {
                                Badge(
                                    containerColor = Color.Black.copy(alpha = 0.6f),
                                    contentColor = Color.White
                                ) { Text(wishlistCount.toString()) }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = stringResource(R.string.wishlist),
                                tint = Color.Red
                            )
                        }
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
