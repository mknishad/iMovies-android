package com.mknishad.imovies.presentation.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.usecases.GetMovieByIdUseCase
import com.mknishad.imovies.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieById: GetMovieByIdUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailsState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("movieId")?.let { movieId ->
            if (movieId != -1) {
                getMovieById(movieId).onEach { movie ->
                    _state.update {
                        it.copy(movie = movie)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun toggleWishlist(movie: Movie) {
        viewModelScope.launch {
            toggleFavorite(movie)
        }
    }
}
