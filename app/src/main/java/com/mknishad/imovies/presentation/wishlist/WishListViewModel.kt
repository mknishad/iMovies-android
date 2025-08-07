package com.mknishad.imovies.presentation.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.usecases.GetWishlistUseCase
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
class WishListViewModel @Inject constructor(
    private val getWishlist: GetWishlistUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WishListState())
    val state = _state.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        getWishlist().onEach { movies ->
            _state.update {
                it.copy(movies = movies)
            }
        }.launchIn(viewModelScope)
    }

    fun toggleWishlist(movie: Movie) {
        viewModelScope.launch {
            toggleFavorite(movie)
        }
    }
}
