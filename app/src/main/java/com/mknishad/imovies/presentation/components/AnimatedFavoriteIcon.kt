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
import androidx.compose.ui.res.stringResource
import com.mknishad.imovies.R

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
        ) { favorite ->
            if (favorite) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(R.string.remove_from_favorites),
                    tint = tint
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.add_to_favorites),
                    tint = tint
                )
            }
        }
    }
}
