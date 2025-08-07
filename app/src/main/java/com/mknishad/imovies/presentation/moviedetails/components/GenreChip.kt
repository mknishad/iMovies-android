package com.mknishad.imovies.presentation.moviedetails.components // Or your preferred package

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreChip(genre: String) {
    SuggestionChip(
        onClick = { /* Handle genre click if needed, e.g., navigate to movies by genre */ },
        label = { Text(genre, style = MaterialTheme.typography.labelSmall) },
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
            enabled = true,
            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    )
}