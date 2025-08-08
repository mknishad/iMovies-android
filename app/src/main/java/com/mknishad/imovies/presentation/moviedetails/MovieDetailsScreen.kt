package com.mknishad.imovies.presentation.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.mknishad.imovies.R
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.presentation.components.AnimatedFavoriteIcon
import com.mknishad.imovies.presentation.main.Screen
import com.mknishad.imovies.presentation.moviedetails.components.GenreChip
import com.mknishad.imovies.presentation.moviedetails.components.SectionTitle
import com.mknishad.imovies.presentation.ui.theme.IMoviesTheme


@Composable
fun MovieDetailsScreen(onNavigateBack: () -> Unit) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MovieDetailsContent(state, onNavigateBack, viewModel::toggleWishlist)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsContent(
    state: MovieDetailsState,
    onNavigateBack: () -> Unit,
    onFavoriteClick: (Movie) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Screen.MovieDetails.title))
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Make the content scrollable
                .padding(innerPadding)
                .padding(bottom = 16.dp)// Padding at the very bottom of scrollable content
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.movie?.posterUrl) // Fallback to poster if no backdrop
                    .crossfade(true)
                    .placeholder(R.mipmap.ic_launcher_foreground)
                    .error(R.mipmap.ic_launcher_foreground)
                    .build(),
                contentDescription = "Movie Backdrop",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), verticalAlignment = Alignment.Top
            ) {
                // Poster Image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(state.movie?.posterUrl)
                        .crossfade(true)
                        .placeholder(R.mipmap.ic_launcher_foreground)
                        .error(R.mipmap.ic_launcher_foreground)
                        .build(),
                    contentDescription = state.movie?.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(130.dp)
                        .aspectRatio(2f / 3f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.1f
                            )
                        )
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Title, Year, Runtime, Director
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = state.movie?.title ?: "",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Year: ${state.movie?.year}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Runtime: ${state.movie?.runtime}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Director: ${state.movie?.director}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    AnimatedFavoriteIcon(
                        isFavorite = state.movie?.isFavorite == 1,
                        onClick = {
                            state.movie?.let {
                                onFavoriteClick(it)
                            }
                        }
                    )
                }
            }

            // Genres
            SectionTitle(title = "Genres")
            state.movie?.genres?.let { genres ->
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(genres.size) { index ->
                        GenreChip(genres[index])
                    }
                }
            } ?: Text("N/A")

            // Plot Summary
            SectionTitle(title = "Plot Summary")
            Text(
                text = state.movie?.plot ?: "N/A",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Actors
            SectionTitle(title = "Cast")
            Text(
                text = state.movie?.actors ?: "N/A",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp)) // Extra space at the end
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsContentPreview() {
    IMoviesTheme {
        MovieDetailsContent(
            state = MovieDetailsState(
                movie = Movie(
                    id = 1,
                    title = "Awesome Mock Movie Adventure",
                    plot = "In a world of mock data, one movie stands to demonstrate the power of Compose Previews and unit tests. Follow our hero as they navigate complex UI states and asynchronous operations.",
                    posterUrl = "https://example.com/mock_poster.jpg", // Replace with a real placeholder URL if needed for image loading tests
                    year = "2024",
                    runtime = "125 min",
                    genres = listOf("Action", "Comedy", "Mockumentary"),
                    director = "Dr. Compose",
                    actors = "Render McState, Effect Handler, Pixel Perfect"
                )
            ),
            onNavigateBack = {},
            onFavoriteClick = {}
        )
    }
}
