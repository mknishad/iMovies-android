package com.mknishad.imovies.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedFavoriteIcon(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.Red.copy(alpha = 0.7f)
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Crossfade(
            targetState = isFavorite,
            animationSpec = tween(durationMillis = 300),
            label = "FavoriteIconCrossfade"
        ) { favorited ->
            if (favorited) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Remove from Wishlist",
                    tint = tint
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Add to Wishlist",
                    tint = tint
                )
            }
        }
    }
}