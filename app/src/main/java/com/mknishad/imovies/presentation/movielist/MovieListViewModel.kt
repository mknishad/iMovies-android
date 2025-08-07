package com.mknishad.imovies.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mknishad.imovies.domain.usecases.GetMoviesFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesFromDatabase: GetMoviesFromDatabaseUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListState())
    val state = _state.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        getMoviesFromDatabase().onEach { movies ->
            _state.update {
                it.copy(movies = movies)
            }
        }.launchIn(viewModelScope)
    }
}
