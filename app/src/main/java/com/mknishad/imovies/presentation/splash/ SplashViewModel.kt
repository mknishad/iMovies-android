package com.mknishad.imovies.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mknishad.imovies.common.Resource
import com.mknishad.imovies.domain.usecases.GetMovieCountUseCase
import com.mknishad.imovies.domain.usecases.GetMoviesFromNetworkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getMovieCount: GetMovieCountUseCase,
    private val getMoviesFromNetwork: GetMoviesFromNetworkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        getMovies() // Fetch movies on initialization
    }

    fun getMovies() {
        viewModelScope.launch {
            if (getMovieCount() == 0) { // If no movies in database, fetch from network
                getMoviesFromNetwork().onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoadFinished = true,
                                    error = "",
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    error = resource.message ?: "An unexpected error occurred",
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _state.update {
                    it.copy(isLoading = false, isLoadFinished = true)
                }
            }
        }
    }
}
