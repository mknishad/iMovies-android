package com.mknishad.imovies.presentation.components // Or your preferred package

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mknishad.imovies.R
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.presentation.ui.theme.IMoviesTheme

@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit,
    onFavoriteClick: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(movie) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Min), // Ensures Row height fits content, good for Column weights
            verticalAlignment = Alignment.Top
        ) {
            // Movie Poster
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(movie.posterUrl)
                    .crossfade(true).build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .aspectRatio(2f / 3f) // Typical movie poster aspect ratio
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)) // Placeholder bg
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Movie Details
            Column(
                modifier = Modifier.weight(1f), // Takes remaining space
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onFavoriteClick(movie) }) {
                        Icon(
                            imageVector = if (movie.isFavorite == 1) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = if (movie.isFavorite == 1) {
                                stringResource(R.string.remove_from_favorites)
                            } else {
                                stringResource(R.string.add_to_favorites)
                            },
                            tint = Color.Red
                        )
                    }
                }

                Text(
                    text = "Year: ${movie.year}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (movie.genres.isNotEmpty()) {
                    Text(
                        text = "Genre: ${movie.genres.firstOrNull() ?: "N/A"}", // Display first genre
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "Runtime: ${movie.runtime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.plot,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieListItemPreview() {
    IMoviesTheme {
        MovieListItem(
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
            ),
            onItemClick = {},
            onFavoriteClick = {}
        )
    }
}
