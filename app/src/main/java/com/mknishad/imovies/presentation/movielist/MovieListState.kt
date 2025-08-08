package com.mknishad.imovies.presentation.movielist

import androidx.paging.PagingData
import com.mknishad.imovies.domain.model.Genre
import com.mknishad.imovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieListState(
    // Data for the list itself
    val movies: Flow<PagingData<Movie>> = emptyFlow(), // Initialize with emptyFlow

    // Genre filter related state
    val availableGenres: List<Genre> = emptyList(), // Use Genre objects
    val selectedGenre: Genre? = null, // Can be null if "All" or no selection yet
    val isDropdownExpanded: Boolean = false,

    // Search related state
    val searchQuery: String = "", // New state for search query
    val isSearchActive: Boolean = false, // To control visibility/focus of search bar

    // Loading/Error states for the main list
    val isLoading: Boolean = true, // For initial load or full screen loading
    val error: String? = null // For general errors
)
