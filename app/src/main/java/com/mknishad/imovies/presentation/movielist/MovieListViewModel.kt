package com.mknishad.imovies.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mknishad.imovies.domain.model.Genre
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.usecases.GetGenresUseCase
import com.mknishad.imovies.domain.usecases.GetMoviesByGenreUseCase
import com.mknishad.imovies.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesByGenre: GetMoviesByGenreUseCase,
    private val getGenres: GetGenresUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListState())
    val state = _state.asStateFlow()

    // Keep track of the current movies flow to avoid re-creating it unnecessarily
    // if only other parts of the state (like dropdown visibility) change.
    private var currentMoviesFlow: Flow<PagingData<Movie>> = emptyFlow()

    init {
        loadInitialGenres()
        // Initialize movies flow based on the initial state (null genre means all)
        updateMoviesForGenre(null)
    }

    private fun loadInitialGenres() {
        viewModelScope.launch {
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
    }

    private fun updateMoviesForGenre(genre: Genre?) {
        // Only create a new flow if the genre actually changes
        // or if the flow hasn't been initialized yet.
        val genreIdToFilter = if (genre == Genre.ALL) null else genre?.name

        currentMoviesFlow = getMoviesByGenre(genreIdToFilter)
            .cachedIn(viewModelScope)

        _state.update {
            it.copy(
                movies = currentMoviesFlow,
                isLoading = false, // PagingData will handle its own loading states
                error = null
            )
        }
    }

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
                        isLoading = true // Indicate loading for new genre
                    )
                }
                // Trigger movie list update for the new genre
                updateMoviesForGenre(genre)
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
