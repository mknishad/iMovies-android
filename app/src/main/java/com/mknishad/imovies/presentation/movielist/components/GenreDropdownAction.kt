package com.mknishad.imovies.presentation.movielist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mknishad.imovies.R
import com.mknishad.imovies.domain.model.Genre

@Composable
fun GenreDropdownAction(
    availableGenres: List<Genre>,
    selectedGenre: Genre?,
    isExpanded: Boolean,
    onToggleDropdown: () -> Unit,
    onGenreSelected: (Genre) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) { // Box is used to anchor the DropdownMenu
        Row(
            modifier = Modifier
                .clickable(onClick = onToggleDropdown)
                .padding(horizontal = 12.dp, vertical = 8.dp), // Padding for clickable area
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_filter_list_24), // Icon indicating filtering
                contentDescription = stringResource(R.string.filter_by_genre),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = selectedGenre?.name ?: "",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.defaultMinSize(minWidth = 50.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = if (isExpanded) "Collapse genre filter" else "Expand genre filter",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onDismiss
        ) {
            availableGenres.forEach { genre ->
                DropdownMenuItem(
                    text = { Text(genre.name) },
                    onClick = {
                        onGenreSelected(genre)
                        // onDismiss() is called within onGenreSelected in ViewModel in this setup
                    }
                )
            }
        }
    }
}