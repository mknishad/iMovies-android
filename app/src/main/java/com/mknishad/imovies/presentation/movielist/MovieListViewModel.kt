package com.mknishad.imovies.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mknishad.imovies.domain.model.Genre
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.usecases.GetGenresUseCase
import com.mknishad.imovies.domain.usecases.GetMoviesByGenreAndQueryUseCase
import com.mknishad.imovies.domain.usecases.GetWishlistCountUseCase
import com.mknishad.imovies.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getWishlistCount: GetWishlistCountUseCase,
    private val getMoviesByGenreAndQuery: GetMoviesByGenreAndQueryUseCase,
    private val getGenres: GetGenresUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListState())
    val state = _state.asStateFlow()

    // Keep track of the current movies flow to avoid re-creating it unnecessarily
    // if only other parts of the state (like dropdown visibility) change.
    private var currentMoviesFlow: Flow<PagingData<Movie>> = emptyFlow()

    // Separate flow for search query to apply debounce
    private val _searchQueryInternal = MutableStateFlow("")

    init {
        loadInitialGenres()
        observeWishlistCount()

        // Combine selectedGenre and debounced searchQuery to trigger movie updates
        viewModelScope.launch {
            combine(
                _state.map { it.selectedGenre }.distinctUntilChanged(),
                _searchQueryInternal.debounce(500L) // Debounce search query by 500ms
            ) { genre, query ->
                Pair(genre, query)
            }.flatMapLatest { (genre, query) ->
                val genreToFilter = if (genre == Genre.ALL) null else genre?.name
                _state.update { it.copy(isLoading = true) } // Show loading for new filter/search
                currentMoviesFlow = getMoviesByGenreAndQuery(genreToFilter, query)
                    .cachedIn(viewModelScope)
                currentMoviesFlow
            }.collect { pagingData ->
                _state.update {
                    it.copy(
                        movies = currentMoviesFlow, // Wrap in flowOf for consistency if needed, or assign directly
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
        // Initialize with default/empty search
        _searchQueryInternal.value = ""
    }

    private fun loadInitialGenres() {
        getGenres().onEach { genres ->
            _state.update {
                it.copy(
                    availableGenres = listOf(Genre.ALL) + genres,
                    selectedGenre = Genre.ALL, // Default selection
                    isLoading = true // Start loading movies
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observeWishlistCount() {
        getWishlistCount().onEach { count ->
            _state.update { it.copy(wishlistCount = count) }
        }.launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        _searchQueryInternal.value = query // Update the internal flow that's debounced
    }

    // Handle toggle search action
    fun onToggleSearch() {
        val newSearchState = !_state.value.isSearchActive
        _state.update { it.copy(isSearchActive = newSearchState) }
        if (!newSearchState) {
            // If search is deactivated, clear query and refresh list
            onSearchQueryChanged("")
        }
    }

    // Handle toggle wishlist action
    fun toggleWishlist(movie: Movie) {
        viewModelScope.launch {
            toggleFavorite(movie)
        }
    }

    // Handle genre selection
    fun onGenreSelected(genre: Genre) {
        viewModelScope.launch {
            if (_state.value.availableGenres.contains(genre)) {
                _state.update {
                    it.copy(
                        selectedGenre = genre,
                        isDropdownExpanded = false, // Close dropdown
                    )
                }
            }
        }
    }

    // Toggle genre dropdown state
    fun toggleGenreDropdown() {
        viewModelScope.launch {
            _state.update {
                it.copy(isDropdownExpanded = !it.isDropdownExpanded)
            }
        }
    }

    // Dismiss genre dropdown
    fun dismissGenreDropdown() {
        viewModelScope.launch {
            _state.update {
                it.copy(isDropdownExpanded = false)
            }
        }
    }
}
