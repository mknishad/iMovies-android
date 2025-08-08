package com.mknishad.imovies.presentation.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.usecases.GetWishlistUseCase
import com.mknishad.imovies.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WishlistViewModel @Inject constructor(
    getWishlist: GetWishlistUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WishlistState())
    val state = _state.asStateFlow()

    val movies: Flow<PagingData<Movie>> = getWishlist()
        .cachedIn(viewModelScope)

    fun toggleWishlist(movie: Movie) {
        viewModelScope.launch {
            toggleFavorite(movie)
        }
    }
}
